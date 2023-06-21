package io.github.v1serviceapplication.domain.weekendmealcheck.mapper;

import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;

public interface WeekendMealCheckMapper {
    WeekendMealCheck entityToDomain(WeekendMealCheckEntity weekendMealCheckEntity);
    WeekendMealCheckEntity domainToEntity(WeekendMealCheck weekendMealCheck);
}
