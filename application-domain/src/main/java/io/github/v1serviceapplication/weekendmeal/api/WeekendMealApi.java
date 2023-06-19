package io.github.v1serviceapplication.weekendmeal.api;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;

import java.util.UUID;

public interface WeekendMealApi {
    void postWeekendMealApply(WeekendMealApplicationStatus status);
    QueryWeekendMealResponse queryWeekendMeal();
    WeekendMealListResponse queryWeekendMealUserList(Integer grade, Integer classNum);
    void postWeekendMealCheck(boolean isCheck);
    void changeStudentWeekendMealApplyStatus(UUID studentId, WeekendMealApplicationStatus status);
}
