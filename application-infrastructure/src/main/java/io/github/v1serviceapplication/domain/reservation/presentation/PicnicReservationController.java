package io.github.v1serviceapplication.domain.reservation.presentation;

import io.github.v1serviceapplication.domain.reservation.presentation.dto.request.ReserveWeekendPicnicRequest;
import io.github.v1serviceapplication.reservation.api.PicnicReservationApi;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "주말 외출 예약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/picnic-reservation")
public class PicnicReservationController {

    private final PicnicReservationApi picnicReservationApi;

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
}
