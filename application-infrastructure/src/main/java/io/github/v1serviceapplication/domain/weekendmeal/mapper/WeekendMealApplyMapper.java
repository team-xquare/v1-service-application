package io.github.v1serviceapplication.domain.weekendmeal.mapper;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealApplyEntity;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;

public interface WeekendMealApplyMapper {
    WeekendMealApplyEntity domainToEntity(WeekendMealApply weekendMealApply);

    WeekendMealApply entityToDomain(WeekendMealApplyEntity weekendMeal);
}
