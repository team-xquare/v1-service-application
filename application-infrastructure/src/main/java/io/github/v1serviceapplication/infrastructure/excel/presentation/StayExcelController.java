package io.github.v1serviceapplication.infrastructure.excel.presentation;

import io.github.v1serviceapplication.infrastructure.excel.StayStatusExcel;
import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayApplyListResponse;
import io.github.v1serviceapplication.infrastructure.excel.service.StayExcelService;
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
@RequestMapping("/stay")
public class StayExcelController {

    private final StayStatusExcel stayStatusExcel;
    private final StayExcelService stayExcelService;


    @Operation(summary = "잔류신청 현황 엑셀 다운로드 API")
    @GetMapping("/excel")
    public void stay(HttpServletResponse response) throws IOException {

        Workbook workbook = stayStatusExcel.createWorkHook();

        String filename = "잔류신청명단.xlsx";

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("KSC5601"), "8859_1"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Operation(summary = "잔류신청 현황 조회 API")
    @GetMapping("/list")
    public StayApplyListResponse queryStayList() {
        return stayExcelService.getStayApplyList();
    }
}
