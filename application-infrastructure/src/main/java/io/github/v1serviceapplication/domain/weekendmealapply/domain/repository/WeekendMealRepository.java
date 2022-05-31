package io.github.v1serviceapplication.domain.weekendmealapply.domain.repository;

import io.github.v1serviceapplication.domain.weekendmealapply.domain.WeekendMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeekendMealRepository extends JpaRepository<WeekendMealEntity, UUID> {
}
