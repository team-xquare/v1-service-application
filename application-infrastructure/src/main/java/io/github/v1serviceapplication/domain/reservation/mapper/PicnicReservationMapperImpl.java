package io.github.v1serviceapplication.domain.reservation.mapper;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationId;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import org.springframework.stereotype.Component;

@Component
public class PicnicReservationMapperImpl implements PicnicReservationMapper {

    @Override
    public PicnicReservationEntity picnicReservationDomainToEntity(PicnicReservation picnicReservation) {
        return PicnicReservationEntity.builder()
                .id(PicnicReservationId.builder()
                        .date(picnicReservation.getDate())
                        .userId(picnicReservation.getUserId())
                        .build())
                .isReserved(picnicReservation.getIsReserved())
                .build();
    }

    @Override
    public PicnicReservation picnicReservationEntityToDomain(PicnicReservationEntity picnicReservationEntity) {
        if (picnicReservationEntity == null) {
            return null;
        }

        return PicnicReservation.builder()
                .date(picnicReservationEntity.getId().getDate())
                .userId(picnicReservationEntity.getId().getUserId())
                .isReserved(picnicReservationEntity.getIsReserved())
                .build();
    }
}
