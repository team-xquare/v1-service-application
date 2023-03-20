package io.github.v1serviceapplication.domain.reservation.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import io.github.v1serviceapplication.domain.reservation.mapper.PicnicReservationMapper;
import io.github.v1serviceapplication.error.PicnicReservationNotFoundException;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import io.github.v1serviceapplication.reservation.spi.PicnicReservationRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.v1serviceapplication.domain.reservation.domain.QPicnicReservationEntity.picnicReservationEntity;

@Repository
@RequiredArgsConstructor
public class PicnicReservationSpiImpl implements PicnicReservationRepositorySpi {
    private final PicnicReservationRepository picnicReservationRepository;
    private final PicnicReservationMapper picnicReservationMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public void reserveWeekendPicnic(PicnicReservation picnicReservation) {
        picnicReservationRepository.save(picnicReservationMapper.picnicReservationDomainToEntity(picnicReservation));
    }

    @Transactional
    @Override
    public void cancelWeekendPicnic(UUID picnicReservationId) {
        queryFactory
                .delete(picnicReservationEntity)
                .where(picnicReservationEntity.id.eq(picnicReservationId))
                .execute();
    }

    @Transactional
    @Override
    public PicnicReservation getPicnicReservationById(UUID picnicReservationId) {
        PicnicReservationEntity picnicReservationEntity = picnicReservationRepository.findById(picnicReservationId)
                .orElseThrow(() -> PicnicReservationNotFoundException.EXCEPTION);

        return picnicReservationMapper.picnicReservationEntityToDomain(picnicReservationEntity);
    }

    @Override
    public List<PicnicReservation> getPicnicReservationListByDate(LocalDate date) {
        List<PicnicReservationEntity> picnicReservationEntityList = picnicReservationRepository.findAllByDate(date);
        return picnicReservationEntityList.stream().map(picnicReservationMapper::picnicReservationEntityToDomain).collect(Collectors.toList());
    }
}