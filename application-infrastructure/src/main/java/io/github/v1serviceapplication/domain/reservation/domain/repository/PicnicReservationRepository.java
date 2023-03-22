package io.github.v1serviceapplication.domain.reservation.domain.repository;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface PicnicReservationRepository extends JpaRepository<PicnicReservationEntity, UUID> {
    List<PicnicReservationEntity> findAllByDate(LocalDate date);

    boolean existsByUserIdAndDate(UUID userId, LocalDate date);

    Optional<PicnicReservationEntity> findByUserIdAndDate(UUID userId, LocalDate date);
}
