package io.github.v1serviceapplication.weekendmeakcheck.spi;

import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;

import java.util.UUID;

public interface PostWeekendMealCheckRepositorySpi {
    void postWeekendMealCheck(WeekendMealCheck weekendMealCheck);

    void changeWeekendMealIsCheck(UUID weekendMealId, boolean isCheck);
}
