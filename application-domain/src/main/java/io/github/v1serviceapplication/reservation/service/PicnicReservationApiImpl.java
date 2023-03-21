package io.github.v1serviceapplication.reservation.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.error.CannotReservePicnicException;
import io.github.v1serviceapplication.error.PicnicReservationNotFoundException;
import io.github.v1serviceapplication.error.PicnicReserveNotAvailableException;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.spi.PicnicUserFeignSpi;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import io.github.v1serviceapplication.reservation.api.PicnicReservationApi;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationElement;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;
import io.github.v1serviceapplication.reservation.spi.PicnicReservationRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class PicnicReservationApiImpl implements PicnicReservationApi {

    private final PicnicReservationRepositorySpi picnicReservationRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final PicnicUserFeignSpi picnicUserFeignSpi;

    static final LocalDate currentDate = LocalDate.now();
    static final LocalTime currentTIme = LocalTime.now();
    static final LocalTime endTime = LocalTime.of(23, 0);

    @Override
    public void reserveWeekendPicnic() {
        DayOfWeek day = currentDate.getDayOfWeek();
        UUID userId = userIdFacade.getCurrentUserId();

        if (day != DayOfWeek.FRIDAY) {
            throw CannotReservePicnicException.EXCEPTION;
        }

        if (currentTIme.isAfter(endTime)) {
            throw PicnicReserveNotAvailableException.EXCEPTION;
        }

        PicnicReservation picnicReservation = PicnicReservation.builder()
                .userId(userId)
                .date(currentDate)
                .build();

        picnicReservationRepositorySpi.reserveWeekendPicnic(picnicReservation);
    }

    @Override
    public void cancelWeekendPicnic(UUID picnicReservationId) {
        if (!isExistsPicnicReservation(picnicReservationId)) {
            throw PicnicReservationNotFoundException.EXCEPTION;
        }

        picnicReservationRepositorySpi.cancelWeekendPicnicById(picnicReservationId);
    }

    @Override
    public PicnicReservationListResponse getPicnicReservationList() {
        LocalDate friday = currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        List<PicnicReservation> picnicReservationList = picnicReservationRepositorySpi.getPicnicReservationListByDate(friday);
        List<UUID> picnicReservationIdList = picnicReservationList.stream()
                .map(PicnicReservation::getUserId)
                .toList();

        Map<UUID, PicnicUserElement> userByIdMap = picnicUserFeignSpi.getUserInfoByUserId(picnicReservationIdList)
                .stream()
                .collect(Collectors.toMap(PicnicUserElement::getUserId, Function.identity()));

        List<PicnicReservationElement> picnicReservationElementList = picnicReservationList.stream()
                .map(picnicReservation -> {
                    PicnicUserElement user = userByIdMap.get(picnicReservation.getUserId());
                    return PicnicReservationElement.builder()
                            .id(picnicReservation.getId())
                            .num(user.getNum())
                            .name(user.getName())
                            .build();
                })
                .toList();

        return new PicnicReservationListResponse(picnicReservationElementList);
    }

    private boolean isExistsPicnicReservation(UUID picnicReservationId) {
        return picnicReservationRepositorySpi.isExistsPicnicReservationById(picnicReservationId);
    }
}
