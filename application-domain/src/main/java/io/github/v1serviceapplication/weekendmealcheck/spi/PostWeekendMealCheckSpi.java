package io.github.v1serviceapplication.weekendmealcheck.spi;

import io.github.v1serviceapplication.weekendmealcheck.WeekendMealCheck;

public interface PostWeekendMealCheckSpi {
    void saveWeekendMealCheck(WeekendMealCheck weekendMealCheck);
}
