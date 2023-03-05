package io.github.v1serviceapplication.infrastructure.excel;

import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealElement;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WeekendMealStatusExcel {

    private final WeekendMealApi weekendMealApi;

    public Workbook createWorkHook() {
        Workbook workbook = new XSSFWorkbook();
        CellStyle mergeRowStyle = workbook.createCellStyle();
        mergeRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        Sheet sheet = workbook.createSheet("주말급식신청조회");

        Row row1 = sheet.createRow(1);

        setHeaderRow(row1);

        List<WeekendMealElement> weekendMealUserList = weekendMealApi.queryWeekendMealUserList().getStudents();
        int i = 2;
        int j = 1;
        for (WeekendMealElement weekendMealElement : weekendMealUserList) {

            Row row = sheet.createRow(i);
            row.createCell(j).setCellValue(weekendMealElement.getNum());
            row.createCell(j + 1).setCellValue(weekendMealElement.getName());
            i++;
        }

        return workbook;
    }

    private void setHeaderRow(Row row) {
        row.createCell(1).setCellValue("학번");
        row.createCell(2).setCellValue("이름");
    }
}
