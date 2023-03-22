package io.github.v1serviceapplication.reservation.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.reservation.PicnicReservation;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@Spi
public
interface PicnicReservationRepositorySpi {

    void reserveWeekendPicnic(PicnicReservation picnicReservation);

    void updateWeekendPicnicReserve(UUID userId, LocalDate date, boolean reserve);

    List<PicnicReservation> getPicnicReservationListByDate(LocalDate date);

    boolean isExistsPicnicReservationByUserIdAndDate(UUID userId, LocalDate date);
}
