package io.github.v1serviceapplication.domain.reservation.presentation;

import io.github.v1serviceapplication.domain.reservation.presentation.dto.request.ReserveWeekendPicnicRequest;
import io.github.v1serviceapplication.infrastructure.excel.PicnicReservationExel;
import io.github.v1serviceapplication.reservation.api.PicnicReservationApi;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "주말 외출 예약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/picnic-reservation")
public class PicnicReservationController {

    private final PicnicReservationApi picnicReservationApi;
    private final PicnicReservationExel picnicReservationExel;

    @Operation(summary = "주말 외출 예약 및 업데이트 API")
    @PostMapping
    public void reserveWeekendPicnic(@Valid @RequestBody ReserveWeekendPicnicRequest request) {
        picnicReservationApi.reserveWeekendPicnic(request.isReserved());
    }

    @Operation(summary = "주말 외출 예약자 조회 API")
    @GetMapping
    public PicnicReservationListResponse getWeekendPicnicReservation() {
        return picnicReservationApi.getPicnicReservationList();
    }

    @Operation(summary = "주말 외출 예약자 현황 엑셀 다운로드 API")
    @GetMapping("/excel")
    public void excel(HttpServletResponse response) throws IOException {
        Workbook workbook = picnicReservationExel.createWorkHook();

        String filename = "주말외출예약자.xlsx";

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("KSC5601"), "8859_1"));

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
