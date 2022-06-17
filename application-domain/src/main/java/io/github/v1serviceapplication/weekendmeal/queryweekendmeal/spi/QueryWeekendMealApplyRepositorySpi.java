package io.github.v1serviceapplication.weekendmeal.queryweekendmeal.spi;

import java.util.UUID;

public interface QueryWeekendMealApplyRepositorySpi {
    boolean queryWeekendMealAppliedApplyByUserIdAndWeekendMealId(UUID userId, UUID weekendMealId);
}
