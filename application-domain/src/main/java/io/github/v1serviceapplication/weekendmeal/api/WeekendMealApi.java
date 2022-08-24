package io.github.v1serviceapplication.weekendmeal.api;

import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;

import java.util.UUID;

public interface WeekendMealApi {
    void postWeekendMealApply(boolean apply, UUID userId);
    QueryWeekendMealResponse queryWeekendMeal(UUID userId);
}
