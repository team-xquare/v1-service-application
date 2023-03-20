package io.github.v1serviceapplication.reservation.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.error.CannotReservePicnicTimeException;
import io.github.v1serviceapplication.error.PicnicReserveNotAvailableException;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.spi.PicnicUserFeignSpi;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import io.github.v1serviceapplication.reservation.api.PicnicReservationApi;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationElement;
import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;
import io.github.v1serviceapplication.reservation.api.dto.ReserveWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.reservation.spi.PicnicReservationRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
public class PicnicReservationApiImpl implements PicnicReservationApi {

    private final PicnicReservationRepositorySpi picnicReservationRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final PicnicUserFeignSpi picnicUserFeignSpi;

    @Override
    public void reserveWeekendPicnic() {
        LocalTime nowTime = LocalTime.now();
        LocalTime endTime = LocalTime.of(23, 0);
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        UUID userId = userIdFacade.getCurrentUserId();

        if (day != DayOfWeek.FRIDAY) {
            throw CannotReservePicnicTimeException.EXCEPTION;
        }

        if (nowTime.isAfter(endTime)) {
            throw PicnicReserveNotAvailableException.EXCEPTION;
        }

        PicnicReservation picnicReservation = PicnicReservation.builder()
                .userId(userId)
                .date(LocalDate.now())
                .build();

        picnicReservationRepositorySpi.reserveWeekendPicnic(picnicReservation);
    }

    @Override
    public void
    cancelWeekendPicnic(UUID picnicReservationId) {
        PicnicReservation picnicReservation = picnicReservationRepositorySpi.getPicnicReservationById(picnicReservationId);
        picnicReservationRepositorySpi.cancelWeekendPicnic(picnicReservation.getId());
    }

    @Override
    public PicnicReservationListResponse getPicnicReservationList() {
        LocalDate now = LocalDate.now();
        DayOfWeek day = now.getDayOfWeek();
        List<PicnicReservation> picnicReservationList = new ArrayList<>(List.of());
        List<UUID> picnicReservationIdList = new ArrayList<>(List.of());

        switch (day) {
            case FRIDAY -> {
                picnicReservationList.addAll(
                        picnicReservationRepositorySpi.getPicnicReservationListByDate(now));
                picnicReservationIdList.addAll(
                        picnicReservationRepositorySpi.getPicnicReservationListByDate(now)
                                .stream()
                                .map(PicnicReservation::getUserId).toList());
            }
            case SATURDAY -> {
                picnicReservationList.addAll(picnicReservationRepositorySpi.getPicnicReservationListByDate(now.minusDays(1)));
                picnicReservationIdList.addAll(
                        picnicReservationRepositorySpi.getPicnicReservationListByDate(now.minusDays(1))
                                .stream()
                                .map(PicnicReservation::getUserId).toList());
            }
            case SUNDAY -> {
                picnicReservationList.addAll(picnicReservationRepositorySpi.getPicnicReservationListByDate(now.minusDays(2)));
                picnicReservationIdList.addAll(
                        picnicReservationRepositorySpi.getPicnicReservationListByDate(now.minusDays(2))
                                .stream()
                                .map(PicnicReservation::getUserId).toList());
            }
        }

        Map<UUID, PicnicUserElement> hashMap = picnicUserFeignSpi.getUserInfoByUserId(picnicReservationIdList).stream()
                .collect(Collectors.toMap(PicnicUserElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<PicnicReservationElement> picnicReservationElementList = picnicReservationList.stream()
                .map(picnicReservation -> {
                    PicnicUserElement user = hashMap.get(picnicReservation.getUserId());
                    return PicnicReservationElement.builder()
                            .id(picnicReservation.getId())
                            .num(user.getNum())
                            .name(user.getName())
                            .build();
                }).toList();

        return new PicnicReservationListResponse(picnicReservationElementList);
    }
}
