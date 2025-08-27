package com.farfarcoder.scm.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelToSqlGenerator {
    //C:\LP_DEV\excel
    private static final String EXCEL_FILE_PATH = "C:\\LP_DEV\\excel\\dummy3.xlsx";
    private static final String SQL_OUTPUT_PATH = "C:\\LP_DEV\\sql\\sql_output.sql";

    public void processExcelToSqlFile() {
        try {
            System.out.println("=== Excel to SQL Insert Generator 시작 ===");
            System.out.println("엑셀 파일 경로: " + EXCEL_FILE_PATH);
            System.out.println("SQL 출력 파일 경로: " + SQL_OUTPUT_PATH);

            // 1. 엑셀 파일 읽기
            List<ProjectData> projectList = readExcelFile(EXCEL_FILE_PATH);
            System.out.println("✓ 엑셀 파일 읽기 완료. 총 " + projectList.size() + "건의 데이터 발견");

            // 2. SQL INSERT 문 생성 및 파일 저장
            generateSqlFile(projectList, SQL_OUTPUT_PATH);
            System.out.println("✓ SQL INSERT 문 파일 생성 완료");

            // 3. 처리 결과 요약 출력
            printProcessSummary(projectList);

            System.out.println("=== 처리 완료 ===");

        } catch (Exception e) {
            System.err.println("❌ 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<ProjectData> readExcelFile(String filePath) throws Exception {
        List<ProjectData> projectList = new ArrayList<>();

        System.out.println("📖 엑셀 파일 읽는 중...");
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);

        Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트

        System.out.println("   - 시트명: " + sheet.getSheetName());
        System.out.println("   - 총 행 수: " + (sheet.getLastRowNum() + 1));

        // 헤더 행 출력
        Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            System.out.print("   - 헤더: ");
            for (int i = 0; i < 13; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    System.out.print("[" + i + "]" + getCellValue(cell) + " ");
                }
            }
            System.out.println();
        }

        // 헤더 행 건너뛰고 데이터 행부터 읽기
        int validDataCount = 0;
        int emptyRowCount = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                emptyRowCount++;
                continue;
            }

            ProjectData project = new ProjectData();

            // 엑셀 컬럼 순서에 맞게 데이터 읽기
            project.bizdiv = getCellValue(row.getCell(0));           // 업무구분
            project.bizmgr = getCellValue(row.getCell(1));           // 업무담당자
            project.config = getCellValue(row.getCell(2));           // 형상
            project.dev = getCellValue(row.getCell(3));              // 개발
            project.oper = getCellValue(row.getCell(4));             // 운영
            project.status = getCellValue(row.getCell(5));           // 상태
            project.bitbucketname = getCellValue(row.getCell(6));    // bitbucketname
            project.bitbucketkey = getCellValue(row.getCell(7));     // bitbucketkey
            project.bitbucketdesc = getCellValue(row.getCell(8));    // bitbucketDescription
            project.bambooname = getCellValue(row.getCell(9));       // BambooName
            project.bambookey = getCellValue(row.getCell(10));       // BambooKey
            project.bamboodesc = getCellValue(row.getCell(11));      // BambooDescription
            project.deploymgr = getCellValue(row.getCell(12));       // 배포담당자

            // 최소한 업무구분이 있는 경우만 유효 데이터로 처리
            if (!project.bizdiv.isEmpty()) {
                projectList.add(project);
                validDataCount++;

                // 처음 3개 데이터는 상세 출력
                if (validDataCount <= 3) {
                    System.out.println("   - [" + i + "행] " + project.toString());
                }
            } else {
                emptyRowCount++;
            }
        }

        System.out.println("   - 유효 데이터: " + validDataCount + "건");
        System.out.println("   - 빈 행: " + emptyRowCount + "건");

        workbook.close();
        fileInputStream.close();

        return projectList;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private void generateSqlFile(List<ProjectData> projectList, String outputPath) throws IOException {
        System.out.println("📝 SQL INSERT 문 생성 중...");

        FileWriter writer = new FileWriter(outputPath, false); // 기존 파일 덮어쓰기
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        // 파일 헤더 작성
        bufferedWriter.write("-- PROJMGMT 테이블 INSERT 문\n");
        bufferedWriter.write("-- 생성일시: " + new java.util.Date() + "\n");
        bufferedWriter.write("-- 총 데이터 건수: " + projectList.size() + "\n\n");

        int successCount = 0;
        int errorCount = 0;

        for (int i = 0; i < projectList.size(); i++) {
            ProjectData project = projectList.get(i);

            try {
                String sql = generateInsertSql(project);
                bufferedWriter.write(sql);
                bufferedWriter.newLine();
                successCount++;

                // 진행상황 출력 (10건마다)
                if ((i + 1) % 10 == 0 || i == 0) {
                    System.out.println("   - " + (i + 1) + "건 처리 완료");
                }

            } catch (Exception e) {
                System.err.println("   ❌ " + (i + 1) + "번째 데이터 처리 중 오류: " + e.getMessage());
                bufferedWriter.write("-- ERROR: " + e.getMessage() + "\n");
                errorCount++;
            }
        }

        // 파일 푸터 작성
        bufferedWriter.write("\n-- 처리 결과\n");
        bufferedWriter.write("-- 성공: " + successCount + "건\n");
        bufferedWriter.write("-- 오류: " + errorCount + "건\n");

        bufferedWriter.close();
        writer.close();

        System.out.println("   - SQL 파일 생성 완료: " + outputPath);
        System.out.println("   - 성공: " + successCount + "건, 오류: " + errorCount + "건");
    }

    private String generateInsertSql(ProjectData project) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO PROJMGMT (");
        sql.append("BIZDIV, BIZMGR, CONFIG, DEV, OPER, STATUS, ");
        sql.append("BITBUCKETNAME, BITBUCKETKEY, BITBUCKETDESC, ");
        sql.append("BAMBOONAME, BAMBOOKEY, BAMBOODESC, DEPLOYMGR");
        sql.append(") VALUES (");

        // SQL Injection 방지를 위해 작은따옴표 이스케이프 처리
        sql.append("'").append(escapeSql(project.bizdiv)).append("', ");
        sql.append("'").append(escapeSql(project.bizmgr)).append("', ");
        sql.append("'").append(escapeSql(project.config)).append("', ");
        sql.append("'").append(escapeSql(project.dev)).append("', ");
        sql.append("'").append(escapeSql(project.oper)).append("', ");
        sql.append("'").append(escapeSql(project.status)).append("', ");
        sql.append("'").append(escapeSql(project.bitbucketname)).append("', ");
        sql.append("'").append(escapeSql(project.bitbucketkey)).append("', ");
        sql.append("'").append(escapeSql(project.bitbucketdesc)).append("', ");
        sql.append("'").append(escapeSql(project.bambooname)).append("', ");
        sql.append("'").append(escapeSql(project.bambookey)).append("', ");
        sql.append("'").append(escapeSql(project.bamboodesc)).append("', ");
        sql.append("'").append(escapeSql(project.deploymgr)).append("'");

        sql.append(");");

        return sql.toString();
    }

    private String escapeSql(String value) {
        if (value == null) return "";
        return value.replace("'", "''"); // SQL에서 작은따옴표 이스케이프
    }

    private void printProcessSummary(List<ProjectData> projectList) {
        System.out.println("\n📊 처리 결과 요약:");
        System.out.println("   - 총 처리된 데이터: " + projectList.size() + "건");

        // 상태별 통계
        long configYCount = projectList.stream().filter(p -> "Y".equals(p.config)).count();
        long devYCount = projectList.stream().filter(p -> "Y".equals(p.dev)).count();
        long operYCount = projectList.stream().filter(p -> "Y".equals(p.oper)).count();

        System.out.println("   - 형상 완료(Y): " + configYCount + "건");
        System.out.println("   - 개발 완료(Y): " + devYCount + "건");
        System.out.println("   - 운영 완료(Y): " + operYCount + "건");

        // 첫 번째와 마지막 데이터 샘플 출력
        if (!projectList.isEmpty()) {
            System.out.println("\n📋 데이터 샘플:");
            System.out.println("   - 첫 번째: " + projectList.get(0).toString());
            if (projectList.size() > 1) {
                System.out.println("   - 마지막: " + projectList.get(projectList.size() - 1).toString());
            }
        }
    }

    // 프로젝트 데이터를 담는 클래스
    static class ProjectData {
        String bizdiv = "";
        String bizmgr = "";
        String config = "";
        String dev = "";
        String oper = "";
        String status = "";
        String bitbucketname = "";
        String bitbucketkey = "";
        String bitbucketdesc = "";
        String bambooname = "";
        String bambookey = "";
        String bamboodesc = "";
        String deploymgr = "";

        @Override
        public String toString() {
            return String.format("업무구분='%s', 담당자='%s', 상태='%s', BitKey='%s'",
                    bizdiv, bizmgr, status, bitbucketkey);
        }
    }

    public static void main(String[] args) {
        ExcelToSqlGenerator generator = new ExcelToSqlGenerator();
        generator.processExcelToSqlFile();
    }
}
