package io.github.v1serviceapplication.domain.reservation.presentation;

import io.github.v1serviceapplication.domain.reservation.presentation.dto.request.ReserveWeekendPicnicRequest;
import io.github.v1serviceapplication.reservation.api.PicnicReservationApi;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;
import io.github.v1serviceapplication.reservation.api.dto.ReserveWeekendPicnicDomainRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "주말 외출 예약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/picnic-reservation")
public class PicnicReservationController {

    private final PicnicReservationApi picnicReservationApi;

    @Operation(summary = "주말 외출 예약 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void reserveWeekendPicnic() {
        picnicReservationApi.reserveWeekendPicnic();
    }

    @Operation(summary = "주말 외출 예약 취소 API")
    @DeleteMapping("/{picnic-reservation-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelWeekendPicnicReservation(@PathVariable("picnic-reservation-id") UUID picnicReservationId) {
        picnicReservationApi.cancelWeekendPicnic(picnicReservationId);
    }

    @Operation(summary = "주말 외출 예약자 조회 API")
    @GetMapping
    public PicnicReservationListResponse getWeekendPicnicReservation() {
        return picnicReservationApi.getPicnicReservationList();
    }
}
