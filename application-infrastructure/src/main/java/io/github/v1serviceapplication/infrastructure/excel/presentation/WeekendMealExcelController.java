package io.github.v1serviceapplication.infrastructure.excel.presentation;

import io.github.v1serviceapplication.infrastructure.excel.WeekendMealStatusExcel;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weekend-meal")
public class WeekendMealExcelController {

    private final WeekendMealStatusExcel weekendMealStatusExcel;

    @Operation(summary = "주말급식 신청 현황 엑셀 다운로드 API")
    @GetMapping("/excel")
    public void weekendMeal(HttpServletResponse response) throws IOException {

        Workbook workbook = weekendMealStatusExcel.createWorkHook();

        String filename = "주말외출명단.xlsx";

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("KSC5601"), "8859_1"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
