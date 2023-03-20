package io.github.v1serviceapplication.domain.reservation.mapper;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PicnicReservationMapper {
    PicnicReservationEntity picnicReservationDomainToEntity(PicnicReservation picnicReservation);

    PicnicReservation picnicReservationEntityToDomain(PicnicReservationEntity picnicReservationEntity);
}
