package io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;

public interface PostWeekendMealApplyRepositorySpi {
    void saveWeekendMeal(WeekendMealApply weekendMealApply);
}
