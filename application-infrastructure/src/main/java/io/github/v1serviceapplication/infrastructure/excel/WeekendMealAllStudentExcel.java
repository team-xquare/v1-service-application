package io.github.v1serviceapplication.infrastructure.excel;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealCheckTeacherElement;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealElement;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WeekendMealAllStudentExcel {

    private final WeekendMealApi weekendMealApi;

    public Workbook createWorkHook() {
        Workbook workbook = new XSSFWorkbook();
        CellStyle headerStyle = workbook.createCellStyle();
        CellStyle colorStyle = workbook.createCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();

        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.forInt((short)1));

        colorStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        colorStyle.setFillPattern(FillPatternType.forInt((short)1));

        applyThinStyle(headerStyle);
        applyThinStyle(colorStyle);
        applyThinStyle(cellStyle);

        Sheet sheet = workbook.createSheet("주말급식전체학생조회");

        Row row1 = sheet.createRow(1);

        setHeaderRow(row1, headerStyle);

        List<WeekendMealElement> responseStudents = weekendMealApi.queryWeekendMealUserList(null, null).getResponseStudents();
        List<WeekendMealElement> nonResponseStudents = weekendMealApi.queryWeekendMealUserList(null, null).getNonResponseStudents();
        List<WeekendMealCheckTeacherElement> weekendMealCheckList = weekendMealApi.weekendMealExcelUserList().getTeacherCheckLists();

        setWeekendMealResponseStudents(sheet, colorStyle, headerStyle, cellStyle, responseStudents, nonResponseStudents, weekendMealCheckList);

        return workbook;
    }

    private void setWeekendMealResponseStudents(Sheet sheet, CellStyle colorStyle, CellStyle headerStyle,
                                                CellStyle cellStyle, List<WeekendMealElement> responseStudents,
                                                List<WeekendMealElement> nonResponseStudents,
                                                List<WeekendMealCheckTeacherElement> weekendMealCheckList) {
        int startRow = 2;
        int startColumn = 1;
        Integer previousGrade = 1; // 이전 학년을 나타내는 변수


        for (WeekendMealElement weekendMealElement : responseStudents) {

            int currentGrade = Integer.parseInt(weekendMealElement.getNum().substring(0, 1));

            if (previousGrade != currentGrade) {
                // 이전 학년과 동일하지 않은 경우 새로운 학년 열을 생성
                startColumn = (currentGrade - 1) * 4 + 1;
                startRow = startRow > 2 ? startRow = 2 : startRow;
                previousGrade = currentGrade; // 이전 학년 변수를 업데이트
            }

            Row row = sheet.getRow(startRow) == null ? sheet.createRow(startRow) : sheet.getRow(startRow);

            Cell num = row.createCell(startColumn);
            Cell name = row.createCell(startColumn + 1);
            Cell applyStatus = row.createCell(startColumn + 2);
            Cell notApplyStatus = row.createCell(startColumn + 3);

            if (weekendMealElement.getStatus().equals(WeekendMealApplicationStatus.NOT_APPLY)) {
                notApplyStatus.setCellValue(String.valueOf(weekendMealElement.getStatus().getValue()));
            } else {
                applyStatus.setCellValue(String.valueOf(weekendMealElement.getStatus().getValue()));
            }

            if (Integer.parseInt(weekendMealElement.getNum().substring(1, 2)) % 2 == 0) {
                num.setCellStyle(colorStyle);
                name.setCellStyle(colorStyle);
                if (row.getCell(startColumn + 2) == null) {
                    applyStatus.setCellStyle(colorStyle);
                    row.getCell(startColumn + 3).setCellStyle(colorStyle);
                } else {
                    row.getCell(startColumn + 2).setCellStyle(colorStyle);
                    notApplyStatus.setCellStyle(colorStyle);
                }
            } else {
                num.setCellStyle(cellStyle);
                name.setCellStyle(cellStyle);
                applyStatus.setCellStyle(cellStyle);
                notApplyStatus.setCellStyle(cellStyle);
            }

            num.setCellValue(weekendMealElement.getNum());

            name.setCellValue(weekendMealElement.getName());

            startRow++;
        }

        startRow = 2;

        for (WeekendMealElement weekendMealElement : nonResponseStudents) {
            Row row = sheet.getRow(startRow) == null ? sheet.createRow(startRow) : sheet.getRow(startRow);

            row.createCell(13).setCellValue(weekendMealElement.getNum());
            row.createCell(14).setCellValue(weekendMealElement.getName());
            row.createCell(15).setCellValue(String.valueOf(weekendMealElement.getStatus().getValue()));

            startRow++;
        }

        setWeekendMealCheckHeaderRow(sheet, startRow, headerStyle, cellStyle);
        startRow = startRow + 2;
        for (WeekendMealCheckTeacherElement weekendMealCheck : weekendMealCheckList) {

            Row row = sheet.getRow(startRow) == null ? sheet.createRow(startRow) : sheet.getRow(startRow);
            String num = weekendMealCheck.getGrade() + " - " + weekendMealCheck.getClassNum();

            row.createCell(13).setCellValue(num);
            row.createCell(14).setCellValue(weekendMealCheck.getName());
            String creatDate = String.valueOf(weekendMealCheck.getCreateDate());
            row.createCell(15).setCellValue(creatDate);

            startRow++;
        }
    }

    private void setWeekendMealCheckHeaderRow(Sheet sheet, int startRow, CellStyle headerStyle, CellStyle cellStyle) {
        Row row = sheet.getRow(startRow + 1) == null ? sheet.createRow(startRow + 1) : sheet.getRow(startRow + 1);
        headerStyle.setAlignment(HorizontalAlignment.forInt((short) 2));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 1, startRow + 1, 13, 15));
        Cell cell = row.createCell(13);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("담임선생님 확인 명단");

        for (int i = 14; i <= 15; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
        }
    }

    private void setHeaderRow(Row row, CellStyle headerStyle) {
        String[] header = {"학번", "이름", "신청", "미신청"};
        headerStyle.setAlignment(HorizontalAlignment.forInt((short) 2));

        for (int i = 1; i <= 12; i++) {
            Cell cell = row.createCell(i);
            int index = i % 4 == 0 ? 3 : i % 4 - 1;
            cell.setCellValue(header[index]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void applyThinStyle(CellStyle headerStyle) {
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
    }
}
