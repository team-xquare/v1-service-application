package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StayRepository extends JpaRepository<StayEntity, UUID> {
}
