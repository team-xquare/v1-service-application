package io.github.v1serviceapplication.domain.reservation.domain.repository;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PicnicReservationRepository extends JpaRepository<PicnicReservationEntity, UUID> {
    List<PicnicReservationEntity> findAllByDate(LocalDate date);

    boolean existsByUserIdAndDate(UUID userId, LocalDate date);

    @Modifying
    @Query("update PicnicReservationEntity r set r.isReserved = :isReserved where r.userId = :userId and r.date = :date")
    void updatePicnicReservation(UUID userId, LocalDate date, boolean isReserved);
}
