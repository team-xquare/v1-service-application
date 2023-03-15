package io.github.v1serviceapplication.infrastructure.excel;

import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.StayStatus;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StayStatusExcel {
    private final StayApi stayApi;

    public Workbook createWorkHook() {
        Workbook workbook = new XSSFWorkbook();
        CellStyle mergeRowStyle = workbook.createCellStyle();

        mergeRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        Sheet sheet = workbook.createSheet("잔류신청명단");

        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(24);
        Row row3 = sheet.createRow(47);

        setHeaderRow(row1);
        setHeaderRow(row2);
        setHeaderRow(row3);

        List<StayStatus> stayStatuses = stayApi.queryStayApplyList().getStudents();

        // row: 23n - 21 + num -1, n은 학년, num은 학번
        // column: 4n - 3 , n은 반
        for (StayStatus stayStatus : stayStatuses) {
            int grade = Integer.parseInt(String.valueOf(stayStatus.getNum().charAt(0)));
            int cls = Integer.parseInt(String.valueOf(stayStatus.getNum().charAt(1)));
            int number = Integer.parseInt(stayStatus.getNum().substring(2, 4));

            int rowNo = 23 * grade - 22 + number; // 2
            int columnNo = 4 * cls - 3; // 1

            Row row = sheet.getRow(rowNo) == null ? sheet.createRow(rowNo) : sheet.getRow(rowNo);
            row.createCell(columnNo++).setCellValue(stayStatus.getNum());
            row.createCell(columnNo++).setCellValue(stayStatus.getName());
            row.createCell(columnNo).setCellValue(stayStatus.getStay());
        }

        return workbook;
    }

    private void setHeaderRow(Row row) {
        row.createCell(1).setCellValue("학번");
        row.createCell(2).setCellValue("이름");
        row.createCell(4).setCellValue("서명");
        row.createCell(5).setCellValue("학번");
        row.createCell(6).setCellValue("이름");
        row.createCell(8).setCellValue("서명");
        row.createCell(9).setCellValue("학번");
        row.createCell(10).setCellValue("이름");
        row.createCell(12).setCellValue("서명");
        row.createCell(13).setCellValue("학번");
        row.createCell(14).setCellValue("이름");
        row.createCell(16).setCellValue("서명");
    }

/*    private void setColor(CellStyle mergeRowStyle) {
        mergeRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        mergeRowStyle.setFillPattern(CellStyle);
    }*/
}
