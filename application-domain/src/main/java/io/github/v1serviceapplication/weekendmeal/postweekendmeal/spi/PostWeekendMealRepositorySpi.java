package io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.WeekendMeal;

public interface PostWeekendMealRepositorySpi {
    void saveWeekendMeal(WeekendMeal weekendMeal);
}
