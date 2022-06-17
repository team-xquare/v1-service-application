package io.github.v1serviceapplication.weekendmeal.queryweekendmeal.spi;

import java.util.UUID;

public interface QueryWeekendMealApplyRepositorySpi {
    boolean queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(UUID userId, UUID weekendMealId);
}
