package io.github.v1serviceapplication.weekendmeakcheck.spi;

import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;

import java.util.Optional;
import java.util.UUID;

public interface QueryWeekendMealCheckRepositorySpi {
   Optional<WeekendMealCheck> queryWeekendMealCheckByWeekendMealIdAndUserId(UUID weekendMealId, UUID userId);
}
