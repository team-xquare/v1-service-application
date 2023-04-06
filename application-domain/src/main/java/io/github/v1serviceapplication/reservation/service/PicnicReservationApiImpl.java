package io.github.v1serviceapplication.reservation.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.common.UserIdFacade;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class PicnicReservationApiImpl implements PicnicReservationApi {

    private final PicnicReservationRepositorySpi picnicReservationRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final PicnicUserFeignSpi picnicUserFeignSpi;

    static final LocalTime endTime = LocalTime.of(23, 0);

    @Override
    public void reserveWeekendPicnic(boolean reserved) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DayOfWeek day = currentDate.getDayOfWeek();
        UUID currentUserId = userIdFacade.getCurrentUserId();

        if (day != DayOfWeek.FRIDAY || currentTime.isAfter(endTime)) {
            throw PicnicReserveNotAvailableException.EXCEPTION;
        }

        picnicReservationRepositorySpi.saveOrUpdateWeekendPicnicReserve(
                currentDate, currentUserId, reserved
        );
    }

    @Override
    public PicnicReservationListResponse getPicnicReservationList() {
        LocalDate currentDate = LocalDate.now();
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
                            .num(user.getNum())
                            .name(user.getName())
                            .reserved(picnicReservation.getIsReserved())
                            .build();
                })
                .toList();

        return new PicnicReservationListResponse(picnicReservationElementList);
    }
}
