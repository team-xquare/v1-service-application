package io.github.v1serviceapplication.reservation.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.error.PicnicReserveNotAvailableException;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import io.github.v1serviceapplication.reservation.api.PicnicReservationApi;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationElement;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;
import io.github.v1serviceapplication.reservation.spi.PicnicReservationRepositorySpi;
import io.github.v1serviceapplication.user.UserIdFacade;
import io.github.v1serviceapplication.user.spi.UserFeignSpi;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class PicnicReservationApiImpl implements PicnicReservationApi {

    private final PicnicReservationRepositorySpi picnicReservationRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final UserFeignSpi userFeignSpi;

    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    @Override
    public void reserveWeekendPicnic(boolean reserved) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DayOfWeek day = currentDate.getDayOfWeek();
        UUID currentUserId = userIdFacade.getCurrentUserId();

        if (day != DayOfWeek.FRIDAY || currentTime.isAfter(END_TIME)) {
            throw PicnicReserveNotAvailableException.EXCEPTION;
        }

        picnicReservationRepositorySpi.saveOrUpdateWeekendPicnicReserve(
                currentDate, currentUserId, reserved
        );
    }

    @Override
    public PicnicReservationListResponse getPicnicReservationList() {
        LocalDate friday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        List<UUID> picnicReservationIdList = picnicReservationRepositorySpi.getPicnicReservationListByDateAndIsReserved(friday)
                .stream()
                .map(PicnicReservation::getUserId)
                .toList();

        List<PicnicReservationElement> picnicReservationElementList = userFeignSpi.getUserInfoList(picnicReservationIdList)
                .stream()
                .map(user -> PicnicReservationElement.builder()
                        .num(user.getNum())
                        .name(user.getName())
                        .build()
                ).sorted(Comparator.comparing(PicnicReservationElement::getNum))
                .toList();

        return new PicnicReservationListResponse(picnicReservationElementList);
    }
}
