package io.github.v1serviceapplication.weekendmeal.queryweekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.WeekendMeal;

public interface QueryWeekendMealRepositorySpi {
    WeekendMeal queryWeekendMealByDate();
}
