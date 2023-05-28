package io.github.v1serviceapplication.domain.weekendmeal.presentation;

import io.github.v1serviceapplication.infrastructure.excel.WeekendMealStatusExcel;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Tag(name = "어드민 주말급식 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminWeekendMealController {

    private final WeekendMealApi weekendMealApi;
    private final WeekendMealStatusExcel weekendMealStatusExcel;

    @Operation(summary = "주말급식 리스트 API")
    @GetMapping("/weekend-meal")
    public WeekendMealListResponse weekendMealUserList() {
        return weekendMealApi.queryWeekendMealUserList();
    }

    @Operation(summary = "주말급식 신청 현황 엑셀 다운로드 API")
    @GetMapping("/weekend-meal/excel")
    public void weekendMeal(HttpServletResponse response) throws IOException {

        Workbook workbook = weekendMealStatusExcel.createWorkHook();

        String filename = "주말외출명단.xlsx";

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("KSC5601"), "8859_1"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Operation(summary = "주말급식 담임선생님 확인 API")
    @PostMapping("/weekend-meal/teacher")
    @ResponseStatus(HttpStatus.CREATED)
    public void weekendMealTeacherCheck(
            @RequestParam(value = "grade") int grade,
            @RequestParam(value = "classNum") int classNum
    ) {
        weekendMealApi.weekendMealTeacherCheck(grade, classNum);
    }
}
