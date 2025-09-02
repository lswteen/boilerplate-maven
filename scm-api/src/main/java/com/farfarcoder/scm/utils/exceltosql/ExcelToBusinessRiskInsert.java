package com.farfarcoder.scm.utils.exceltosql;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileWriter;

public class ExcelToBusinessRiskInsert {
    public static void main(String[] args) throws Exception {
        String excelFilePath = "C:\\LP_DEV\\excel\\dummy4.xlsx";
        String outputFilePath = "C:\\LP_DEV\\sql\\sql_output_risk.sql";

        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        FileWriter sqlWriter = new FileWriter(outputFilePath);
        boolean isFirstRow = true;

        for (Row row : sheet) {
            // 헤더 행 스킵
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            StringBuilder insertQuery = new StringBuilder();
            insertQuery.append("INSERT INTO BUSINESS_RISK (");
            insertQuery.append("GUBUN, BUSINESS, SYSTEM, ");
            insertQuery.append("DETAIL_BUSINESS, DEPT, OPER_DEPT, CONFIG, DEV, OPER, REMARKS");
            insertQuery.append(") VALUES (");

            int[] columnIndexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

            for (int i = 0; i < columnIndexes.length; i++) {
                Cell cell = row.getCell(columnIndexes[i]);
                String value = getCellValue(cell);

                if (value.equals("NULL") || value.isEmpty()) {
                    insertQuery.append("NULL");
                } else {
                    // 점수 컬럼들은 숫자로 처리
                    insertQuery.append("'").append(value.replace("'", "''")).append("'");
                }

                if (i < columnIndexes.length - 1) {
                    insertQuery.append(", ");
                }
            }

            insertQuery.append(");");

            // 콘솔과 파일 모두에 출력
            System.out.println(insertQuery.toString());
            sqlWriter.write(insertQuery.toString() + "\n");
        }

        // 리소스 정리
        sqlWriter.close();
        workbook.close();
        fileInputStream.close();

        System.out.println("\nSQL 파일이 생성되었습니다: " + outputFilePath);
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // 정수로 표시 (소수점 제거)
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    }
                    return String.valueOf(numericValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
