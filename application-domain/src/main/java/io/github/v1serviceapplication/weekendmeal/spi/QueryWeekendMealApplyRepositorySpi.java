package io.github.v1serviceapplication.weekendmeal.spi;

import java.util.UUID;

public interface QueryWeekendMealApplyRepositorySpi {
    boolean queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(UUID userId, UUID weekendMealId);
}
