package io.github.v1serviceapplication.domain.stay.presentation;

import io.github.v1serviceapplication.domain.stay.presentation.dto.request.AdminChangeStayStatusRequest;
import io.github.v1serviceapplication.stay.api.dto.response.StayApplyListResponse;
import io.github.v1serviceapplication.infrastructure.excel.StayStatusExcel;
import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.AdminUserInfoResponse;
import io.github.v1serviceapplication.stay.api.dto.response.QueryAllStayStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Tag(name = "어드민 잔류 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminStayController {

    private final StayApi stayApi;
    private final StayStatusExcel stayStatusExcel;

    @Operation(summary = "어드민 잔류 조회 API")
    @GetMapping("/stay")
    public QueryAllStayStatusResponse getAllStayStatus() {
        return stayApi.queryAllStayStatus();
    }

    @Operation(summary = "어드민 유저 상세보기 API")
    @GetMapping("/stay/{student-id}")
    public AdminUserInfoResponse getAdminUserInfo(@PathVariable("student-id") UUID studentId) {
        return stayApi.queryUserInfo(studentId);
    }

    @Operation(summary = "어드민 잔류 상태 변경 API")
    @PutMapping("/stay/{student-id}")
    public void adminChangeStayStatus(@PathVariable("student-id") UUID studentId, @RequestBody @Valid AdminChangeStayStatusRequest request) {
        stayApi.adminChangeStayStatus(studentId, request.getStatus());
    }

    @Operation(summary = "잔류신청 현황 조회 API")
    @GetMapping("/stay/list")
    public StayApplyListResponse queryStayList() {
        return stayApi.queryStayApplyList();
    }

    @Operation(summary = "잔류신청 현황 엑셀 다운로드 API")
    @GetMapping("/stay/excel")
    public void stay(HttpServletResponse response) throws IOException {

        Workbook workbook = stayStatusExcel.createWorkHook();

        String filename = "잔류신청명단.xlsx";

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("KSC5601"), "8859_1"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
