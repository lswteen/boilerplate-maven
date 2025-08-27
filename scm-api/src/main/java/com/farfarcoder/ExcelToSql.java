package com.farfarcoder;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;

public class ExcelToSql {
    public static void main(String[] args) throws Exception {
        String excelFilePath = "/Users/milk/workspace/excel/excel_dummy_data_2.xlsx";
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            // 각 행을 순회하며 데이터 처리
            for (Cell cell : row) {
                // 각 셀의 데이터 처리
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.print(cell.getDateCellValue() + "\t");
                        } else {
                            System.out.print(cell.getNumericCellValue() + "\t");
                        }
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    case FORMULA:
                        System.out.print(cell.getCellFormula() + "\t");
                        break;
                    default:
                        System.out.print("\t");
                }
            }
            System.out.println();
        }
    }
}
