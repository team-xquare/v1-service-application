package io.github.v1serviceapplication.domain.reservation.mapper;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import io.github.v1serviceapplication.reservation.PicnicReservation;

public interface PicnicReservationMapper {
    PicnicReservationEntity picnicReservationDomainToEntity(PicnicReservation picnicReservation);

    PicnicReservation picnicReservationEntityToDomain(PicnicReservationEntity picnicReservationEntity);
}
