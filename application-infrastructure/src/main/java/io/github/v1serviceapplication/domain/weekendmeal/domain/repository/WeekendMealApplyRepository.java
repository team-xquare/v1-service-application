package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeekendMealApplyRepository extends JpaRepository<WeekendMealApplyEntity, UUID> {
    Optional<WeekendMealApplyEntity> findTop1ByUserIdOrderByDateAsc(UUID userId);
}
