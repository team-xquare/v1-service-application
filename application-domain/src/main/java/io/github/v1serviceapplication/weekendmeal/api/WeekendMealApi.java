package io.github.v1serviceapplication.weekendmeal.api;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;


public interface WeekendMealApi {
    void postWeekendMealApply(WeekendMealApplicationStatus status);
    QueryWeekendMealResponse queryWeekendMeal();

    WeekendMealListResponse queryWeekendMealUserList();
}
