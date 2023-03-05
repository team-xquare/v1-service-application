package io.github.v1serviceapplication.weekendmeal.api;

import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;


public interface WeekendMealApi {
    void postWeekendMealApply(boolean apply);
    QueryWeekendMealResponse queryWeekendMeal();

    WeekendMealListResponse queryWeekendMealUserList();
}
