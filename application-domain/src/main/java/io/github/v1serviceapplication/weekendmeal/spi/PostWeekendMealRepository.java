package io.github.v1serviceapplication.weekendmeal.spi;

import java.util.UUID;

public interface PostWeekendMealRepository {
    void changeWeekendMealAllowedPeriod(UUID weekendMealId, boolean allowedPeriod);
}
