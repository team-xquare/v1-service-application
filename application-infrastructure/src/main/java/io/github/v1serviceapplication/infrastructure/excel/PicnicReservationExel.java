package io.github.v1serviceapplication.infrastructure.excel;

import io.github.v1serviceapplication.reservation.api.PicnicReservationApi;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationElement;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PicnicReservationExel {

    private final PicnicReservationApi picnicReservationApi;

    public Workbook createWorkHook() {
        Workbook workbook = new XSSFWorkbook();

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        applyThinStyle(headerStyle);

        CellStyle cellStyle = workbook.createCellStyle();
        applyThinStyle(cellStyle);

        Sheet sheet = workbook.createSheet("주말외출예약자");
        Row row1 = sheet.createRow(1);
        setHeaderRow(row1, headerStyle);

        List<PicnicReservationElement> picnicReservationList = picnicReservationApi
                .getPicnicReservationList()
                .getPicnicReservationElementList();

        int i = 2;
        int j = 1;
        for (PicnicReservationElement picnicReservationElement : picnicReservationList) {
            Row row = sheet.createRow(i);
            Cell num = row.createCell(j);
            num.setCellValue(picnicReservationElement.getNum());
            num.setCellStyle(cellStyle);

            Cell name = row.createCell(j + 1);
            name.setCellValue(picnicReservationElement.getName());
            name.setCellStyle(cellStyle);
            i++;
        }

        return workbook;
    }

    private void setHeaderRow(Row row, CellStyle cellStyle) {
        Cell num = row.createCell(1);
        Cell name = row.createCell(2);
        num.setCellValue("학번");
        name.setCellValue("이름");
        num.setCellStyle(cellStyle);
        name.setCellStyle(cellStyle);
    }

    private void applyThinStyle(CellStyle headerStyle) {
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
    }
}
