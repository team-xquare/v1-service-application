package io.github.v1serviceapplication.domain.reservation.mapper;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import org.springframework.stereotype.Component;

@Component
public class PicnicReservationMapperImpl implements PicnicReservationMapper {

    @Override
    public PicnicReservationEntity picnicReservationDomainToEntity(PicnicReservation picnicReservation) {
        return PicnicReservationEntity.builder()
                .id(picnicReservation.getId())
                .userId(picnicReservation.getUserId())
                .date(picnicReservation.getDate())
                .isReserved(picnicReservation.getIsReserved())
                .build();
    }

    @Override
    public PicnicReservation picnicReservationEntityToDomain(PicnicReservationEntity picnicReservationEntity) {
        if (picnicReservationEntity == null) {
            return null;
        }

        return PicnicReservation.builder()
                .id(picnicReservationEntity.getId())
                .userId(picnicReservationEntity.getUserId())
                .date(picnicReservationEntity.getDate())
                .isReserved(picnicReservationEntity.getIsReserved())
                .build();
    }
}
