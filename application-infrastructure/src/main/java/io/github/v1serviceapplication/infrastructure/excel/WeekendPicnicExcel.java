package io.github.v1serviceapplication.infrastructure.excel;

import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.WeekendPicnicExcelElement;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WeekendPicnicExcel {

    private final PicnicApi picnicApi;

    public Workbook createWeekendOutingListWorkBook() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("주말외출현황");

        List<WeekendPicnicExcelElement> weekendPicnicList =
                picnicApi.weekendPicnicExcel().getWeekendPicnicList();

        createWorkBook(workbook, sheet, weekendPicnicList);

        return workbook;
    }

    public void createWorkBook(Workbook workbook, Sheet sheet, List<WeekendPicnicExcelElement> weekendPicnicList) {
        CellStyle cellStyle = workbook.createCellStyle();
        applyThinStyle(cellStyle);
        Row row = sheet.createRow(0);

        setHeaderRow(row, cellStyle);
        setBodyRow(sheet, cellStyle, weekendPicnicList);
    }

    private void setHeaderRow(Row row, CellStyle cellStyle) {
        String[] header = {"학번", "이름", "외출시간", "복귀시간", "외출서명", "복귀학인"};
        for (int i = 0; i < header.length; i++) {
            Cell cell = row.createCell(i + 1);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle);
        }
    }

    private void applyThinStyle(CellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
    }

    private void setBodyRow(Sheet sheet, CellStyle cellStyle, List<WeekendPicnicExcelElement> weekendPicnicList) {
        int i = 1;

        for (WeekendPicnicExcelElement weekendPicnicExcelElement : weekendPicnicList) {
            Row row = sheet.createRow(1);
            Cell num = row.createCell(i++);
            num.setCellValue(weekendPicnicExcelElement.getNum());
            num.setCellStyle(cellStyle);

            Cell name = row.createCell(i++);
            name.setCellValue(weekendPicnicExcelElement.getName());
            name.setCellStyle(cellStyle);

            Cell startTime = row.createCell(i++);
            startTime.setCellValue(String.valueOf(weekendPicnicExcelElement.getStartTime()));
            startTime.setCellStyle(cellStyle);

            Cell endTime = row.createCell(i++);
            endTime.setCellValue(String.valueOf(weekendPicnicExcelElement.getEndTime()));
            endTime.setCellStyle(cellStyle);

            row.createCell(i++).setCellStyle(cellStyle);
            row.createCell(i++).setCellStyle(cellStyle);
        }
    }
}
