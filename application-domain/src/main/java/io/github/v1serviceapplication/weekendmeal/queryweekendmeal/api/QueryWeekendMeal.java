package io.github.v1serviceapplication.weekendmeal.queryweekendmeal.api;

import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.api.dto.QueryWeekendMealResponse;

import java.util.UUID;

public interface QueryWeekendMeal {
    QueryWeekendMealResponse queryWeekendMeal(UUID userId);
}
