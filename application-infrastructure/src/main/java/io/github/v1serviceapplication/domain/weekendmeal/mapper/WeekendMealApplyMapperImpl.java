package io.github.v1serviceapplication.domain.weekendmeal.mapper;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealApplyEntity;
import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealEntity;
import io.github.v1serviceapplication.domain.weekendmeal.domain.repository.WeekendMealRepository;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeekendMealApplyMapperImpl implements WeekendMealApplyMapper {

    private final WeekendMealRepository weekendMealRepository;

    @Override
    public WeekendMealApplyEntity domainToEntity(WeekendMealApply weekendMealApply) {

        WeekendMealEntity weekendMeal = weekendMealRepository.findById(weekendMealApply.getWeekendMealId())
                .orElseThrow(() -> WeekendMealNotFoundException.EXCEPTION);

        return WeekendMealApplyEntity.builder()
                .id(weekendMealApply.getId())
                .userId(weekendMealApply.getUserId())
                .date(weekendMealApply.getDate())
                .weekendMeal(weekendMeal)
                .isApplied(weekendMealApply.getIsApplied())
                .build();
    }

    @Override
    public WeekendMealApply entityToDomain(WeekendMealApplyEntity weekendMealApply) {
        return WeekendMealApply.builder()
                .id(weekendMealApply.getId())
                .userId(weekendMealApply.getUserId())
                .weekendMealId(weekendMealApply.getWeekendMealId())
                .isApplied(weekendMealApply.getIsApplied())
                .build();
    }

}
