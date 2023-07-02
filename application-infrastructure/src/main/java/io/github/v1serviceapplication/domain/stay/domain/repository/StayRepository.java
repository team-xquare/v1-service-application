package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StayRepository extends JpaRepository<StayEntity, UUID> {
    Optional<StayEntity> findByUserId(UUID userId);
    List<UUID> findAllByCode(StayStatusCode status);
}