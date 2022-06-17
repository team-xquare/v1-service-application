package io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;

import java.util.UUID;

public interface PostWeekendMealApplyRepositorySpi {
    boolean currentWeekendMealApplyExist(UUID userId, UUID weekendMealId);
    void saveWeekendMealApply(WeekendMealApply weekendMealApply);
    void updateWeekendMealApply(UUID userId, UUID weekendMealId, Boolean apply);
}
