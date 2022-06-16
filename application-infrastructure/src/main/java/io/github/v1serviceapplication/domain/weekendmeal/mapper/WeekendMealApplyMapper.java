package io.github.v1serviceapplication.domain.weekendmeal.mapper;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealApplyEntity;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeekendMealApplyMapper {
    WeekendMealApplyEntity domainToEntity(WeekendMeal weekendMeal);

    WeekendMeal entityToDomain(WeekendMealApplyEntity weekendMeal);
}
