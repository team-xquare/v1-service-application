package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealApplyMapper;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealApplyApplyRepositoryImpl implements PostWeekendMealApplyRepositorySpi {

    private final WeekendMealApplyRepository weekendMealApplyRepository;
    private final WeekendMealApplyMapper weekendMealApplyMapper;

    @Override
    public void saveWeekendMeal(WeekendMeal weekendMeal) {
        weekendMealApplyRepository.save(
                weekendMealApplyMapper.domainToEntity(weekendMeal)
        );
    }
}
