package io.github.v1serviceapplication.domain.weekendmealcheck.domain.repository;

import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeekendMealCheckRepository extends JpaRepository<WeekendMealCheckEntity, UUID> {
    Optional<WeekendMealCheckEntity> findById(UUID weekendMealCheckId);
}
