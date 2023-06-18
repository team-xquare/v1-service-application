package io.github.v1serviceapplication.weekendmeakcheck.spi;

import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;

import java.util.UUID;

public interface QueryWeekendMealCheckRepositorySpi {
   boolean existsWeekendMealCheck(UUID weekendMealId, UUID userId);
   WeekendMealCheck queryWeekendMealByweekendMealIdAndUserId(UUID weekendMealId, UUID userId);
}
