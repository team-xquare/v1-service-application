package io.github.v1serviceapplication.infrastructure.excel;

import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.WeekendPicnicExcelElement;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WeekendPicnicExcel {

    private final PicnicApi picnicApi;

    public Workbook createWeekendOutingListWorkBook() {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("주말외출현황");
        Sheet sheet2 = workbook.createSheet("주말외출대기현황");

        List<WeekendPicnicExcelElement> weekendPicnicList =
                picnicApi.weekendPicnicExcel().getWeekendPicnicList().stream().filter(picnic -> picnic.getIsAcceptance() == true).toList();

        List<WeekendPicnicExcelElement> weekendPicnicAwaitList =
                picnicApi.weekendPicnicExcel().getWeekendPicnicList().stream().filter(picnic -> picnic.getIsAcceptance() == false).toList();

        createWorkBook(workbook, sheet, weekendPicnicList);
        createWorkBook(workbook, sheet2, weekendPicnicAwaitList);

        return workbook;
    }

    public void createWorkBook(Workbook workbook, Sheet sheet, List<WeekendPicnicExcelElement> weekendPicnicAwaitList) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        Row locationRow = sheet.createRow(0);

        setHeaderRow(locationRow);

        int cellNum = 1;
        int rowNum = 2;
        for (WeekendPicnicExcelElement weekendPicnicExcelElement : weekendPicnicAwaitList) {
            Row row = sheet.createRow(rowNum);
            row.createCell(cellNum).setCellValue(weekendPicnicExcelElement.getName());
            row.createCell(cellNum + 1).setCellValue(weekendPicnicExcelElement.getNum());
            row.createCell(cellNum + 2).setCellValue(String.valueOf(weekendPicnicExcelElement.getStartTime()));
            row.createCell(cellNum + 3).setCellValue(String.valueOf(weekendPicnicExcelElement.getEndTime()));
            row.createCell(cellNum + 4).setCellValue(weekendPicnicExcelElement.getReason());
            row.createCell(cellNum + 5).setCellValue(weekendPicnicExcelElement.getArrangement());
            rowNum++;
        }

    }

    private void setHeaderRow(Row row) {
        String[] cellValues = {"이름", "학번", "외출시간", "귀가시간", "외출사유", "동행인"};
        int number = 1;
        for (String values : cellValues) {
            row.createCell(number++).setCellValue(values);
        }
    }
}
