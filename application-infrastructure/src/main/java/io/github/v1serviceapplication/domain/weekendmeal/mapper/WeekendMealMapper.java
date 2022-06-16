package io.github.v1serviceapplication.domain.weekendmeal.mapper;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealEntity;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeekendMealMapper {

    WeekendMealEntity domainToEntity(WeekendMeal weekendMeal);

    WeekendMeal entityToDomain(WeekendMealEntity weekendMeal);

}
