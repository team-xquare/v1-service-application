package io.github.v1serviceapplication.domain.reservation.domain.repository;

import io.github.v1serviceapplication.domain.reservation.mapper.PicnicReservationMapper;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import io.github.v1serviceapplication.reservation.spi.PicnicReservationRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class PicnicReservationSpiImpl implements PicnicReservationRepositorySpi {
    private final PicnicReservationRepository picnicReservationRepository;
    private final PicnicReservationMapper picnicReservationMapper;

    @Transactional
    @Override
    public void saveOrUpdateWeekendPicnicReserve(LocalDate picnicReservationDate, UUID userId, boolean isReserved) {
        picnicReservationRepository.saveOrUpdatePicnicReservation(
                picnicReservationDate, userId, isReserved
        );
    }

    @Override
    public List<PicnicReservation> getPicnicReservationListByDateAndIsReserved(LocalDate picnicReservationDate) {
        return picnicReservationRepository.findAllById_PicnicReservationDateAndIsReserved(picnicReservationDate, true)
                .stream()
                .map(picnicReservationMapper::picnicReservationEntityToDomain)
                .toList();
    }
}
