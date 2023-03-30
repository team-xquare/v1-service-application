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

    @Override
    public void reserveWeekendPicnic(PicnicReservation picnicReservation) {
        picnicReservationRepository.save(picnicReservationMapper.picnicReservationDomainToEntity(picnicReservation));
    }

    @Transactional
    @Override
    public void updateWeekendPicnicReserve(UUID userId, LocalDate date, boolean reserved) {
        picnicReservationRepository.updatePicnicReservation(userId, date, reserved);
    }

    @Override
    public List<PicnicReservation> getPicnicReservationListByDate(LocalDate date) {
        List<PicnicReservationEntity> picnicReservationEntityList = picnicReservationRepository.findAllByDate(date);
        return picnicReservationEntityList.stream().map(picnicReservationMapper::picnicReservationEntityToDomain).collect(Collectors.toList());
    }

    @Override
    public boolean isExistsPicnicReservationByUserIdAndDate(UUID userId, LocalDate date) {
        return picnicReservationRepository.existsByUserIdAndDate(userId, date);
    }
}
