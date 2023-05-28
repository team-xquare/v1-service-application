package io.github.v1serviceapplication.weekendmealcheck.spi;

import io.github.v1serviceapplication.weekendmealcheck.WeekendMealCheck;

import java.util.UUID;

public interface QueryWeekendMealCheckRepositorySpi {
    Boolean queryWeekendMealCheckByTeacherId(UUID teacherId, UUID weekendMealId);
}
