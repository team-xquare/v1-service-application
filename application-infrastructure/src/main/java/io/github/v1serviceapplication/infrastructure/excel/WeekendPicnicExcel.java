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
        String[] header = {"학번 ", "이름", "외출시간", "복귀시간", "외출서명", "복귀학인"};
        int wholeGrade = 24;
        int oneClass = 6;
        for (int currentClass = 1; currentClass <= wholeGrade; currentClass++) {
            Cell cell = row.createCell(currentClass);
            int index = currentClass % oneClass == 0 ? 3 : currentClass % oneClass - 1;
            cell.setCellValue(header[index]);
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
        for (WeekendPicnicExcelElement weekendPicnicExcelElement : weekendPicnicList) {

            int grade = Integer.parseInt(String.valueOf(weekendPicnicExcelElement.getNum().charAt(0)));
            int cls = Integer.parseInt(String.valueOf(weekendPicnicExcelElement.getNum().charAt(1)));
            int number = Integer.parseInt(weekendPicnicExcelElement.getNum().substring(2, 4));

            int rowNo = 23 * grade - 22 + number; // 2
            int columnNo = 4 * cls - 3; // 1

            Row row = sheet.getRow(rowNo) == null ? sheet.createRow(rowNo) : sheet.getRow(rowNo);

            Cell num = row.createCell(columnNo++);
            num.setCellValue(weekendPicnicExcelElement.getNum());
            num.setCellStyle(cellStyle);

            Cell name = row.createCell(columnNo++);
            name.setCellValue(weekendPicnicExcelElement.getName());
            name.setCellStyle(cellStyle);

            Cell startTime = row.createCell(columnNo);
            startTime.setCellValue(String.valueOf(weekendPicnicExcelElement.getStartTime()));
            startTime.setCellStyle(cellStyle);

            Cell endTime = row.createCell(columnNo);
            endTime.setCellValue(String.valueOf(weekendPicnicExcelElement.getEndTime()));
            endTime.setCellStyle(cellStyle);

            row.createCell(columnNo + 1).setCellStyle(cellStyle);
            row.createCell(columnNo + 2).setCellStyle(cellStyle);
        }
    }
}
