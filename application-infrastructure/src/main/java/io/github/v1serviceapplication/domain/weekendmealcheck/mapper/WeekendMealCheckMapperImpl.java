package io.github.v1serviceapplication.domain.weekendmealcheck.mapper;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealEntity;
import io.github.v1serviceapplication.domain.weekendmeal.domain.repository.WeekendMealRepository;
import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmealcheck.WeekendMealCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeekendMealCheckMapperImpl implements WeekendMealCheckMapper{

    private final WeekendMealRepository weekendMealRepository;
    @Override
    public WeekendMealCheckEntity domainToEntity(WeekendMealCheck weekendMealCheck) {
        WeekendMealEntity weekendMeal = weekendMealRepository.findById(weekendMealCheck.getWeekendMealId())
                .orElseThrow(() -> WeekendMealNotFoundException.EXCEPTION);

        return WeekendMealCheckEntity.builder()
                .id(weekendMealCheck.getId())
                .userId(weekendMealCheck.getUserId())
                .createDate(weekendMealCheck.getCreateDate())
                .grade(weekendMealCheck.getGrade())
                .classNum(weekendMealCheck.getClassNum())
                .weekendMeal(weekendMeal)
                .build();
    }

    @Override
    public WeekendMealCheck entityToDomain(WeekendMealCheckEntity weekendMealCheck) {
        return WeekendMealCheck.builder()
                .id(weekendMealCheck.getId())
                .userId(weekendMealCheck.getUserId())
                .createDate(weekendMealCheck.getCreateDate())
                .grade(weekendMealCheck.getGrade())
                .classNum(weekendMealCheck.getClassNum())
                .weekendMealId(weekendMealCheck.getMealId())
                .build();
    }
}
