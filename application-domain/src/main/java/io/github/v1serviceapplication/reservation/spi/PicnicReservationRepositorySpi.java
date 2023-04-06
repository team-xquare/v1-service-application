package io.github.v1serviceapplication.reservation.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.reservation.PicnicReservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Spi
public
interface PicnicReservationRepositorySpi {

    void saveOrUpdateWeekendPicnicReserve(LocalDate date, UUID userId, boolean reserved);

    List<PicnicReservation> getPicnicReservationListByDate(LocalDate date);
}
