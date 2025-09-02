package com.farfarcoder.scm.utils.excel.renderer;

import com.farfarcoder.scm.utils.excel.annotation.ExcelColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ExcelRenderer {

    /**
     * 리스트 데이터를 Excel 파일로 변환 (집계 정보 포함)
     */
    public byte[] render(List<?> data, Class<?> dtoClass, String sheetName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);

            // 1. @ExcelColumn 어노테이션이 있는 필드 수집 및 정렬
            List<Field> excelFields = getExcelFields(dtoClass);

            if (excelFields.isEmpty()) {
                log.warn("No @ExcelColumn annotated fields found in class: {}", dtoClass.getSimpleName());
                return new byte[0];
            }

            // 2. 스타일 생성
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle summaryStyle = createSummaryStyle(workbook);

            // 3. 집계 정보 생성 (상단)
            int currentRowIndex = createSummarySection(sheet, data.size(), summaryStyle);

            // 4. 빈 행 추가 (구분)
            currentRowIndex++;

            // 5. 헤더 생성
            createHeader(sheet, excelFields, headerStyle, currentRowIndex);

            // 6. 데이터 생성
            createDataRows(sheet, data, excelFields, dataStyle, currentRowIndex + 1);

            // 7. 컬럼 너비 자동 조정
            autoSizeColumns(sheet, excelFields.size());

            // 8. byte[] 반환
            return convertToByteArray(workbook);

        } catch (Exception e) {
            log.error("Excel rendering failed", e);
            throw new RuntimeException("Excel file generation failed", e);
        }
    }

    /**
     * @ExcelColumn 어노테이션이 있는 필드들을 order 순으로 정렬하여 반환
     */
    private List<Field> getExcelFields(Class<?> dtoClass) {
        return Arrays.stream(dtoClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .filter(field -> !field.getAnnotation(ExcelColumn.class).skip())
                .sorted(Comparator.comparing(field ->
                        field.getAnnotation(ExcelColumn.class).order()))
                .collect(Collectors.toList());
    }

    /**
     * 헤더 스타일 생성
     */
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();

        // 배경색 설정 (연한 회색)
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 테두리 설정
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // 폰트 설정
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerStyle.setFont(headerFont);

        // 정렬 설정
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return headerStyle;
    }

    /**
     * 데이터 스타일 생성
     */
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle dataStyle = workbook.createCellStyle();

        // 테두리 설정
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        // 정렬 설정
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return dataStyle;
    }

    /**
     * 집계 정보 스타일 생성
     */
    private CellStyle createSummaryStyle(Workbook workbook) {
        CellStyle summaryStyle = workbook.createCellStyle();

        // 배경색 설정 (연한 파란색)
        summaryStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        summaryStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 테두리 설정
        summaryStyle.setBorderTop(BorderStyle.THIN);
        summaryStyle.setBorderBottom(BorderStyle.THIN);
        summaryStyle.setBorderLeft(BorderStyle.THIN);
        summaryStyle.setBorderRight(BorderStyle.THIN);

        // 폰트 설정
        Font summaryFont = workbook.createFont();
        summaryFont.setBold(true);
        summaryFont.setFontHeightInPoints((short) 12);
        summaryFont.setColor(IndexedColors.DARK_BLUE.getIndex());
        summaryStyle.setFont(summaryFont);

        // 정렬 설정
        summaryStyle.setAlignment(HorizontalAlignment.LEFT);
        summaryStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return summaryStyle;
    }

    /**
     * 집계 정보 섹션 생성 (Excel 상단)
     */
    private int createSummarySection(Sheet sheet, int totalRecords, CellStyle summaryStyle) {
        int currentRowIndex = 0;

        // 제목 행
        Row titleRow = sheet.createRow(currentRowIndex++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("📊 프로젝트-프로젝트관리 데이터 집계");
        titleCell.setCellStyle(summaryStyle);

        // 집계 정보 행
        Row summaryRow = sheet.createRow(currentRowIndex++);
        Cell summaryCell = summaryRow.createCell(0);
        summaryCell.setCellValue(String.format("• 총 레코드 수: %,d건", totalRecords));
        summaryCell.setCellStyle(summaryStyle);

        // 생성 일시 행
        Row dateRow = sheet.createRow(currentRowIndex++);
        Cell dateCell = dateRow.createCell(0);
        String currentDateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateCell.setCellValue(String.format("• 생성 일시: %s", currentDateTime));
        dateCell.setCellStyle(summaryStyle);

        // 집계 섹션의 컬럼들을 병합 (보기 좋게)
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 4)); // 제목 병합
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 4)); // 집계 병합
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(2, 2, 0, 4)); // 날짜 병합

        return currentRowIndex;
    }

    /**
     * 헤더 행 생성
     */
    private void createHeader(Sheet sheet, List<Field> excelFields, CellStyle headerStyle, int rowIndex) {
        Row headerRow = sheet.createRow(rowIndex);
        headerRow.setHeightInPoints(25); // 헤더 높이 설정

        for (int i = 0; i < excelFields.size(); i++) {
            Field field = excelFields.get(i);
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(annotation.headerName());
            cell.setCellStyle(headerStyle);
        }
    }

    /**
     * 데이터 행들 생성
     */
    private void createDataRows(Sheet sheet, List<?> data, List<Field> excelFields, CellStyle dataStyle, int startRowIndex) {
        for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
            Row dataRow = sheet.createRow(startRowIndex + rowIndex);
            Object rowData = data.get(rowIndex);

            for (int colIndex = 0; colIndex < excelFields.size(); colIndex++) {
                Field field = excelFields.get(colIndex);
                Cell cell = dataRow.createCell(colIndex);

                try {
                    field.setAccessible(true);
                    Object value = field.get(rowData);
                    setCellValue(cell, value, field.getAnnotation(ExcelColumn.class));
                    cell.setCellStyle(dataStyle);
                } catch (IllegalAccessException e) {
                    log.warn("Cannot access field: {}", field.getName(), e);
                    cell.setCellValue("");
                    cell.setCellStyle(dataStyle);
                }
            }
        }
    }

    /**
     * 셀에 값 설정 (타입에 따라 처리)
     */
    private void setCellValue(Cell cell, Object value, ExcelColumn annotation) {
        if (value == null) {
            cell.setCellValue("");
            return;
        }

        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Instant) {
            Instant instant = (Instant) value;
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            String formattedDate = localDateTime.format(DateTimeFormatter.ofPattern(annotation.dateFormat()));
            cell.setCellValue(formattedDate);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    /**
     * 컬럼 너비 자동 조정
     */
    private void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);

            // 최대 너비 제한 (Excel에서 너무 넓어지는 것 방지)
            int currentWidth = sheet.getColumnWidth(i);
            int maxWidth = 15000; // 약 15글자 정도
            if (currentWidth > maxWidth) {
                sheet.setColumnWidth(i, maxWidth);
            }
        }
    }

    /**
     * Workbook을 byte array로 변환
     */
    private byte[] convertToByteArray(Workbook workbook) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}
