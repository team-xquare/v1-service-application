package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealApplyMapper;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealApplyRepositoryImpl implements PostWeekendMealApplyRepositorySpi {

    private final WeekendMealApplyRepository weekendMealApplyRepository;
    private final WeekendMealApplyMapper weekendMealApplyMapper;

    @Override
    public void saveWeekendMeal(WeekendMealApply weekendMealApply) {
        weekendMealApplyRepository.save(
                weekendMealApplyMapper.domainToEntity(weekendMealApply)
        );
    }
}
