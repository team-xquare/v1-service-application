package io.github.v1serviceapplication.reservation.api;

import io.github.v1serviceapplication.reservation.api.dto.PicnicReservationListResponse;

public interface PicnicReservationApi {

    void reserveWeekendPicnic(boolean reserve);

    PicnicReservationListResponse getPicnicReservationList();
}
