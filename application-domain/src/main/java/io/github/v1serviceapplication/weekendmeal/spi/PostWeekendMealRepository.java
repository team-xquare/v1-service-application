package io.github.v1serviceapplication.weekendmeal.spi;

import java.util.UUID;

public interface PostWeekendMealRepository {
    void changeAllowedPeriodByWeekendMealIdAndAllowedPeriod(UUID weekendMealId, boolean allowedPeriod);
}
