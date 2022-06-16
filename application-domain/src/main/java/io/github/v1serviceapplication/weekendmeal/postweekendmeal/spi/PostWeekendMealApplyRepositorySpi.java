package io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;

import java.util.UUID;

public interface PostWeekendMealApplyRepositorySpi {
    boolean todayWeekendMealApplyExist(UUID userId);
    void saveWeekendMealApply(WeekendMealApply weekendMealApply);
    void updateWeekendMealApply(UUID userId, Boolean apply);
}
