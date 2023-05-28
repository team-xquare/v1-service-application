package io.github.v1serviceapplication.domain.weekendmealcheck.domain.repository;

import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeekendMealCheckRepository extends CrudRepository<WeekendMealCheckEntity, UUID> {
}
