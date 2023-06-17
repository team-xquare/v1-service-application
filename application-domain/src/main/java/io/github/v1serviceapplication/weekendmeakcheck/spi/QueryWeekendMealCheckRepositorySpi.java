package io.github.v1serviceapplication.weekendmeakcheck.spi;

import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;

import java.util.List;
import java.util.UUID;

public interface QueryWeekendMealCheckRepositorySpi {
   WeekendMealCheck existsWeekendMealCheck(UUID weekendMealId, UUID userId);

   List<WeekendMealCheck> queryWeekendMealCheckList(UUID weekendMealId);
}
