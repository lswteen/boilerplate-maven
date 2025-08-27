package com.farfarcoder.scm.web.dashboard.service;

import com.farfarcoder.scm.domain.dashboard.model.Dashboard;
import com.farfarcoder.scm.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardExcelService {
    private final DashboardService dashboardService;

    public byte[] exportDashboardToExcel(String projectKey) throws IOException {
        List<Dashboard> dashboards = dashboardService.findProjectAndProjMgmtBuilds(projectKey);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // 시트 생성
            Sheet sheet = workbook.createSheet("Dashboard Report");

            // 스타일 설정
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle levelTypeStyle = createLevelTypeStyle(workbook);

            // 제목 및 메타 정보 추가
            createTitleSection(sheet, headerStyle, projectKey);

            // 헤더 생성
            int headerRowIndex = 4;
            createHeaders(sheet, headerStyle, headerRowIndex);

            // 데이터 추가
            int dataRowIndex = headerRowIndex + 1;
            for (Dashboard dashboard : dashboards) {
                createDataRow(sheet, dashboard, dataRowIndex++, dataStyle, levelTypeStyle);
            }

            // 컬럼 자동 크기 조정
            autoSizeColumns(sheet);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private void createTitleSection(Sheet sheet, CellStyle headerStyle, String projectKey) {
        // 제목 행
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("프로젝트 대시보드 리포트");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

        // 생성일시 행
        Row dateRow = sheet.createRow(1);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("생성일시: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // 조회 조건 행
        Row conditionRow = sheet.createRow(2);
        Cell conditionCell = conditionRow.createCell(0);
        String condition = (projectKey != null && !projectKey.isEmpty())
                ? "조회 조건: 프로젝트 키 = " + projectKey
                : "조회 조건: 전체 프로젝트";
        conditionCell.setCellValue(condition);
    }

    private void createHeaders(Sheet sheet, CellStyle headerStyle, int rowIndex) {
        Row headerRow = sheet.createRow(rowIndex);
        String[] headers = {
                "LEVEL", "프로젝트ID", "프로젝트_KEY", "프로젝트명", "프로젝트상세설명",
                "BUILD_번호", "BUILD_제목", "BUILD_타입", "일련번호",
                "BAMBOO_KEY", "업무구분", "업무담당자", "형상관리", "개발", "운영", "진행상태",
                "BITBUCKET_NAME", "BITBUCKET_KEY", "BITBUCKET_DESC", "BAMBOO_DESC", "담당자"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void createDataRow(Sheet sheet, Dashboard dashboard, int rowIndex,
                               CellStyle dataStyle, CellStyle levelTypeStyle) {
        Row row = sheet.createRow(rowIndex);

        // 레벨타입에 따른 스타일 적용
        CellStyle currentStyle = dataStyle;
        if (dashboard.levelType() == 3) { // 매핑안된 데이터
            currentStyle = levelTypeStyle;
        }

        int colIndex = 0;

        // 레벨타입 표시
        Cell levelCell = row.createCell(colIndex++);
        String levelText = switch (dashboard.levelType()) {
            case 1 -> "프로젝트";
            case 2 -> "빌드";
            case 3 -> "매핑없음";
            default -> "기타";
        };
        levelCell.setCellValue(levelText);
        levelCell.setCellStyle(currentStyle);

        // 기본 프로젝트 정보
        createCell(row, colIndex++, dashboard.projectId(), currentStyle);
        createCell(row, colIndex++, dashboard.projectKey(), currentStyle);
        createCell(row, colIndex++, dashboard.title(), currentStyle);
        createCell(row, colIndex++, dashboard.description(), currentStyle);

        // 빌드 정보
        createCell(row, colIndex++, dashboard.buildId(), currentStyle);
        createCell(row, colIndex++, dashboard.buildTitle(), currentStyle);
        createCell(row, colIndex++, dashboard.buildType(), currentStyle);
        createCell(row, colIndex++, dashboard.buildSeq(), currentStyle);

        // PROJMGMT 정보
        createCell(row, colIndex++, dashboard.bambooKey(), currentStyle);
        createCell(row, colIndex++, dashboard.bizDiv(), currentStyle);
        createCell(row, colIndex++, dashboard.bizMgr(), currentStyle);
        createCell(row, colIndex++, dashboard.config(), currentStyle);
        createCell(row, colIndex++, dashboard.dev(), currentStyle);
        createCell(row, colIndex++, dashboard.oper(), currentStyle);
        createCell(row, colIndex++, dashboard.status(), currentStyle);
        createCell(row, colIndex++, dashboard.bitbucketName(), currentStyle);
        createCell(row, colIndex++, dashboard.bitbucketKey(), currentStyle);
        createCell(row, colIndex++, dashboard.bitbucketDesc(), currentStyle);
        createCell(row, colIndex++, dashboard.bambooDesc(), currentStyle);
        createCell(row, colIndex++, dashboard.deployMgr(), currentStyle);
    }

    private void createCell(Row row, int colIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(colIndex);
        if (value != null) {
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else {
                cell.setCellValue(value.toString());
            }
        } else {
            cell.setCellValue("");
        }
        cell.setCellStyle(style);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createLevelTypeStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 매핑안된 데이터는 배경색 변경
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void autoSizeColumns(Sheet sheet) {
        // 헤더 행을 기준으로 컬럼 개수 확인
        Row headerRow = sheet.getRow(4);
        if (headerRow != null) {
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
                // 최대 너비 제한 (50자 정도)
                if (sheet.getColumnWidth(i) > 12800) {
                    sheet.setColumnWidth(i, 12800);
                }
            }
        }
    }

}
