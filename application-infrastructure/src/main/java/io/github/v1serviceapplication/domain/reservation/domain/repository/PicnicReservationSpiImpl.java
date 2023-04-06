package io.github.v1serviceapplication.domain.reservation.domain.repository;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import io.github.v1serviceapplication.domain.reservation.mapper.PicnicReservationMapper;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import io.github.v1serviceapplication.reservation.spi.PicnicReservationRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PicnicReservationSpiImpl implements PicnicReservationRepositorySpi {
    private final PicnicReservationRepository picnicReservationRepository;
    private final PicnicReservationMapper picnicReservationMapper;

    @Transactional
    @Override
    public void saveOrUpdateWeekendPicnicReserve(LocalDate date, UUID userId, boolean isReserved) {
        picnicReservationRepository.saveOrUpdatePicnicReservation(
                date, userId, isReserved
        );
    }

    @Override
    public List<PicnicReservation> getPicnicReservationListByDate(LocalDate date) {
        List<PicnicReservationEntity> picnicReservationEntityList = picnicReservationRepository.findAll()
                .stream()
                .filter(picnicReservationEntity -> picnicReservationEntity.getId().getDate().equals(date))
                .toList();
        return picnicReservationEntityList.stream().map(picnicReservationMapper::picnicReservationEntityToDomain).collect(Collectors.toList());
    }
}
