package io.github.v1serviceapplication.domain.weekendmeal.presentation;

import io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request.ChangeStudentWeekendMealApplyStatusRequest;
import io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request.ChangeWeekendMealAllowedPeriodRequest;
import io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request.PostWeekendMealCheckRequest;
import io.github.v1serviceapplication.infrastructure.excel.WeekendMealAllStudentExcel;
import io.github.v1serviceapplication.infrastructure.excel.WeekendMealStatusExcel;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealAllowedPeriodResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealCheckStatusResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;

@Tag(name = "어드민 주말급식 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminWeekendMealController {

    private final WeekendMealApi weekendMealApi;
    private final WeekendMealAllStudentExcel weekendMealAllStudentExcel;
    private final WeekendMealStatusExcel weekendMealStatusExcel;

    @Operation(summary = "주말급식 리스트 API")
    @GetMapping("/weekend-meal")
    public WeekendMealListResponse weekendMealUserList(
            @RequestParam(value = "grade", required = false) Integer grade,
            @RequestParam(value = "classNum", required = false) Integer classNum
    ) {
        return weekendMealApi.queryWeekendMealUserList(grade, classNum);
    }

    @Operation(summary = "주말급식 신청 현황 엑셀 다운로드 API")
    @GetMapping("/weekend-meal/excel")
    public void weekendMealApplyStatus(HttpServletResponse response) throws IOException {

        Workbook workbook = weekendMealStatusExcel.createWorkHook();

        String filename = LocalDate.now().getYear() + "년도" + LocalDate.now().getMonthValue() + "월 주말급식명단.xlsx";

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", new String(filename.getBytes("KSC5601"), "8859_1") + "attachment;filename=.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Operation(summary = "주말급식 신청 선생님 확인 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/weekend-meal/teacher/check")
    public void weekendMealTeacherCheck(@RequestBody @Valid PostWeekendMealCheckRequest request) {
        weekendMealApi.postWeekendMealCheck(request.getIsCheck(), request.getGrade(), request.getClassNum());
    }

    @Operation(summary = "주말급식 전체 학생 엑셀 다운로드 API")
    @GetMapping("/weekend-meal/all/excel")
    public void weekendMealAllStudentExcel(HttpServletResponse response) throws IOException {
        Workbook workbook = weekendMealAllStudentExcel.createWorkHook();

        String filename = LocalDate.now().getYear() + "년도" + LocalDate.now().getMonth().getValue() + "월 주말급식전체학생명단.xlsx";

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", new String(filename.getBytes("KSC5601"), "8859_1") + "attachment;filename=.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Operation(summary = "주말급식 신청가능 여부 변경 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/change/period")
    public void changeWeekendMealAllowedPeriod(@RequestBody @Valid ChangeWeekendMealAllowedPeriodRequest request) {
        weekendMealApi.changeWeekendMealAllowedPeriod(request.getAllowedPeriod());
    }

    @Operation(summary = "주말급식 학생 신청상태 변경 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/change/student/status")
    public void changeStudentWeekendMealApplyStatus(@RequestBody @Valid ChangeStudentWeekendMealApplyStatusRequest request) {
        weekendMealApi.changeStudentWeekendMealApplyStatus(request.getStudentId(), request.getStatus());
    }

    @Operation(summary = "주말급식 선생님 확인 여부 확인 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check")
    public WeekendMealCheckStatusResponse queryWeekendMealCheckStatus(
            @RequestParam(value = "grade") Integer grade,
            @RequestParam(value = "classNum") Integer classNum
    ) {
        return weekendMealApi.queryWeekendMealCheckStatus(grade, classNum);
    }

    @Operation(summary = "주말급식 허용 기간 조회 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/period")
    public WeekendMealAllowedPeriodResponse queryWeekendMealAllowedPeriod() {
        return weekendMealApi.queryWeekendMealIsAllowedPeriod();
    }
}
