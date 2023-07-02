package io.github.v1serviceapplication.weekendmeal.api;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealAllowedPeriodResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealCheckStatusResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealExcelListResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;

import java.util.List;
import java.util.UUID;

public interface WeekendMealApi {
    void postWeekendMealApply(WeekendMealApplicationStatus status);
    QueryWeekendMealResponse queryWeekendMeal();
    WeekendMealListResponse queryWeekendMealUserList(Integer grade, Integer classNum);
    void changeStudentWeekendMealApplyStatus(UUID studentId, WeekendMealApplicationStatus status);
    void postWeekendMealCheck(boolean isCheck, int grade, int classNum);
    WeekendMealExcelListResponse weekendMealExcelUserList();
    void changeWeekendMealAllowedPeriod(boolean allowPeriod);
    WeekendMealCheckStatusResponse queryWeekendMealCheckStatus(int grade, int classNum);
    WeekendMealAllowedPeriodResponse queryWeekendMealIsAllowedPeriod();
    List<UUID> queryUserIdListByStatus(WeekendMealApplicationStatus status);
}
