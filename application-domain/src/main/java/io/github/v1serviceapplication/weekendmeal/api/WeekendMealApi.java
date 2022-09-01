package io.github.v1serviceapplication.weekendmeal.api;

import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;


public interface WeekendMealApi {
    void postWeekendMealApply(boolean apply);
    QueryWeekendMealResponse queryWeekendMeal();
}
