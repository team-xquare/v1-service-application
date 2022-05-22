package io.github.v1serviceapplication.domain.weekendmealapply.domain.repository;

import io.github.v1serviceapplication.domain.weekendmealapply.domain.WeekendMealApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeekendMealApplyRepository extends JpaRepository<WeekendMealApplyEntity, UUID> {
}
