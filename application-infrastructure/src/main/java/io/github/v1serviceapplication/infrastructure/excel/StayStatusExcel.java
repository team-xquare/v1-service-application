package io.github.v1serviceapplication.infrastructure.excel;

import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayStatus;
import io.github.v1serviceapplication.infrastructure.excel.service.StayExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StayStatusExcel {
    private final StayExcelService stayExcelService;

    public Workbook createWorkHook() {
        Workbook workbook = new XSSFWorkbook();

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        Sheet sheet = workbook.createSheet("잔류신청명단");

        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(24);
        Row row3 = sheet.createRow(47);

        setHeaderRow(row1, headerStyle);
        setHeaderRow(row2, headerStyle);
        setHeaderRow(row3, headerStyle);

        List<StayStatus> stayStatuses = stayExcelService.getStayApplyList().getStudents();

        // row: 23n - 21 + num -1, n은 학년, num은 학번
        // column: 4n - 3 , n은 반
        for (StayStatus stayStatus : stayStatuses) {
            int grade = Integer.parseInt(String.valueOf(stayStatus.getNum().charAt(0)));
            int cls = Integer.parseInt(String.valueOf(stayStatus.getNum().charAt(1)));
            int number = Integer.parseInt(stayStatus.getNum().substring(2, 4));

            int rowNo = 23 * grade - 22 + number; // 2
            int columnNo = 4 * cls - 3; // 1

            Row row = sheet.getRow(rowNo) == null ? sheet.createRow(rowNo) : sheet.getRow(rowNo);
            Cell cell1 = row.createCell(columnNo++);
            cell1.setCellValue(stayStatus.getNum());
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(columnNo++);
            cell2.setCellValue(stayStatus.getName());
            cell2.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(columnNo);
            cell3.setCellValue(stayStatus.getStay());
            cell3.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(columnNo + 1);
            cell4.setCellStyle(cellStyle);
        }

        return workbook;
    }

    private void setHeaderRow(Row row, CellStyle cellStyle) {
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("학번");
        cell1.setCellStyle(cellStyle);

        Cell cell2 = row.createCell(2);
        cell2.setCellValue("이름");
        cell2.setCellStyle(cellStyle);

        Cell cell3 = row.createCell(3);
        cell3.setCellValue("잔류현황");
        cell3.setCellStyle(cellStyle);

        Cell cell4 = row.createCell(4);
        cell4.setCellValue("서명");
        cell4.setCellStyle(cellStyle);

        Cell cell5 = row.createCell(5);
        cell5.setCellValue("학번");
        cell5.setCellStyle(cellStyle);

        Cell cell6 = row.createCell(6);
        cell6.setCellValue("이름");
        cell6.setCellStyle(cellStyle);

        Cell cell7 = row.createCell(7);
        cell7.setCellValue("잔류현황");
        cell7.setCellStyle(cellStyle);

        Cell cell8 = row.createCell(8);
        cell8.setCellValue("서명");
        cell8.setCellStyle(cellStyle);

        Cell cell9 = row.createCell(9);
        cell9.setCellValue("학번");
        cell9.setCellStyle(cellStyle);

        Cell cell10 = row.createCell(10);
        cell10.setCellValue("이름");
        cell10.setCellStyle(cellStyle);

        Cell cell11 = row.createCell(11);
        cell11.setCellValue("잔류현황");
        cell11.setCellStyle(cellStyle);

        Cell cell12 = row.createCell(12);
        cell12.setCellValue("서명");
        cell12.setCellStyle(cellStyle);

        Cell cell13 = row.createCell(13);
        cell13.setCellValue("학번");
        cell13.setCellStyle(cellStyle);

        Cell cell14 = row.createCell(14);
        cell14.setCellValue("이름");
        cell14.setCellStyle(cellStyle);

        Cell cell15 = row.createCell(15);
        cell15.setCellValue("잔류현황");
        cell15.setCellStyle(cellStyle);

        Cell cell16 = row.createCell(16);
        cell16.setCellValue("서명");
        cell16.setCellStyle(cellStyle);
    }
}
