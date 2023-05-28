package io.github.v1serviceapplication.domain.weekendmealcheck.mapper;

import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import io.github.v1serviceapplication.weekendmealcheck.WeekendMealCheck;

public interface WeekendMealCheckMapper {
    WeekendMealCheckEntity domainToEntity(WeekendMealCheck weekendMealCheck);

    WeekendMealCheck entityToDomain(WeekendMealCheckEntity weekendMealCheck);
}
