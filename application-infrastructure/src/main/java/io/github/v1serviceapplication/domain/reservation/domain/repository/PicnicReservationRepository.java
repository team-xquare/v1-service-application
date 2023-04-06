package io.github.v1serviceapplication.domain.reservation.domain.repository;

import io.github.v1serviceapplication.domain.reservation.domain.PicnicReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PicnicReservationRepository extends JpaRepository<PicnicReservationEntity, UUID> {
    @Modifying
    @Query(value = "INSERT INTO tbl_picnic_reservation (date, user_id, is_reserved) VALUES (:date, :userId, :isReserved) ON DUPLICATE KEY UPDATE is_reserved = :isReserved", nativeQuery = true)
    void saveOrUpdatePicnicReservation(LocalDate date, UUID userId, boolean isReserved);
}
