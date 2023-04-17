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
    @Modifying
    @Query(value = "INSERT INTO tbl_picnic_reservation (picnic_reservation_date, user_id, is_reserved) VALUES (:picnicReservationDate, :userId, :isReserved) ON DUPLICATE KEY UPDATE is_reserved = :isReserved", nativeQuery = true)
    void saveOrUpdatePicnicReservation(LocalDate picnicReservationDate, UUID userId, boolean isReserved);

    List<PicnicReservationEntity> findAllById_PicnicReservationDateAndIsReserved(LocalDate picnicReservationDate, boolean isReserved);
}
