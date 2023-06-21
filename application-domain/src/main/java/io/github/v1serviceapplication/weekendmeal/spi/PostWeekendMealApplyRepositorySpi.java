package io.github.v1serviceapplication.weekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;

import java.util.UUID;

public interface PostWeekendMealApplyRepositorySpi {
    void updateWeekendMealApply(UUID userId, UUID weekendMealId, WeekendMealApplicationStatus status);
}
