package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WeekendMealRepository extends CrudRepository<WeekendMealEntity, UUID> {
}
