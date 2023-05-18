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

        setHeaderRow(row, cellStyle, sheet);
        setBodyRow(sheet, cellStyle, weekendPicnicList);
    }

    private void setHeaderRow(Row row, CellStyle cellStyle, Sheet sheet) {
        String[] header = {"학번", "이름", "외출시간", "복귀시간", "외출서명", "복귀확인"};

        for (int i = 0; i < header.length; i++) {
            sheet.setColumnWidth(i+1, (sheet.getColumnWidth(i+1))+1024);
            Cell cell = row.createCell(i + 1);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle);
            cellStyle.setFillBackgroundColor(IndexedColors.YELLOW1.getIndex());
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
            int j = 1;
            Row row = sheet.createRow(i);
            Cell num = row.createCell(j++);
            num.setCellValue(weekendPicnicExcelElement.getNum());
            num.setCellStyle(cellStyle);

            Cell name = row.createCell(j++);
            name.setCellValue(weekendPicnicExcelElement.getName());
            name.setCellStyle(cellStyle);

            Cell startTime = row.createCell(j++);
            startTime.setCellValue(String.valueOf(weekendPicnicExcelElement.getStartTime()));
            startTime.setCellStyle(cellStyle);

            Cell endTime = row.createCell(j++);
            endTime.setCellValue(String.valueOf(weekendPicnicExcelElement.getEndTime()));
            endTime.setCellStyle(cellStyle);

            row.createCell(j++).setCellStyle(cellStyle);
            row.createCell(j).setCellStyle(cellStyle);
            i++;
        }
    }
}
