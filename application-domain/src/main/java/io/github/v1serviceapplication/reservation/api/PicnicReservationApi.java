package io.github.v1serviceapplication.reservation.api;

import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;

import java.util.UUID;

public interface PicnicReservationApi {

    void reserveWeekendPicnic(boolean reserve);

    PicnicReservationListResponse getPicnicReservationList();
}
