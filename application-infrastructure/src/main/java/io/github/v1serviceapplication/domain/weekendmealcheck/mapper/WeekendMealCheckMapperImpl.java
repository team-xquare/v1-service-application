package io.github.v1serviceapplication.domain.weekendmealcheck.mapper;

import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealEntity;
import io.github.v1serviceapplication.domain.weekendmeal.domain.repository.WeekendMealRepository;
import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeekendMealCheckMapperImpl implements WeekendMealCheckMapper{
    private final WeekendMealRepository weekendMealRepository;

    @Override
    public WeekendMealCheck entityToDomain(WeekendMealCheckEntity weekendMealCheckEntity) {
        return WeekendMealCheck.builder()
                .id(weekendMealCheckEntity.getId())
                .userId(weekendMealCheckEntity.getUserId())
                .createDate(weekendMealCheckEntity.getCreateDate())
                .weekendMealId(weekendMealCheckEntity.getWeekendMealId())
                .isCheck(weekendMealCheckEntity.isCheck())
                .build();

    }

    @Override
    public WeekendMealCheckEntity domainToEntity(WeekendMealCheck weekendMealCheck) {
        WeekendMealEntity weekendMeal = weekendMealRepository.findById(weekendMealCheck.getWeekendMealId())
                .orElseThrow(()-> WeekendMealNotFoundException.EXCEPTION);

        return WeekendMealCheckEntity.builder()
                .id(weekendMealCheck.getId())
                .userId(weekendMealCheck.getUserId())
                .createDate(weekendMealCheck.getCreateDate())
                .weekendMeal(weekendMeal)
                .isCheck(weekendMealCheck.isCheck())
                .build();
    }
}
