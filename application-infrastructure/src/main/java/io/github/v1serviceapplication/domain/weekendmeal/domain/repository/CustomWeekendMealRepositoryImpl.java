package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealMapper;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi.PostWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealRepositoryImpl implements PostWeekendMealRepositorySpi {

    private final WeekendMealRepository weekendMealRepository;
    private final WeekendMealMapper weekendMealMapper;

    @Override
    public void saveWeekendMeal(WeekendMeal weekendMeal) {
        weekendMealRepository.save(
                weekendMealMapper.domainToEntity(weekendMeal)
        );
    }
}
