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
        getThinStyle(headerStyle);

        CellStyle cellStyle = workbook.createCellStyle();
        getThinStyle(cellStyle);

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
            Cell studentId = row.createCell(columnNo++);
            studentId.setCellValue(stayStatus.getNum());
            studentId.setCellStyle(cellStyle);

            Cell name = row.createCell(columnNo++);
            name.setCellValue(stayStatus.getName());
            name.setCellStyle(cellStyle);

            Cell status = row.createCell(columnNo);
            status.setCellValue(stayStatus.getStay());
            status.setCellStyle(cellStyle);

            Cell sign = row.createCell(columnNo + 1);
            sign.setCellStyle(cellStyle);
        }

        return workbook;
    }

    private void setHeaderRow(Row row, CellStyle cellStyle) {
        String[] header = new String[]{"학번", "이름", "잔류현황", "서명"};
        int wholeGrade = 16;
        int oneClass = 4;
        for (int currentClass = 1; currentClass <= wholeGrade; currentClass++) {
            Cell cell = row.createCell(currentClass);
            int index = currentClass % oneClass == 0 ? 3 : currentClass % oneClass - 1;
            cell.setCellValue(header[index]);
            cell.setCellStyle(cellStyle);
        }
    }

    private void getThinStyle(CellStyle headerStyle) {
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
    }
}
