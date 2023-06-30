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
<<<<<<< refs/remotes/origin/main
    WeekendMealAllowedPeriodResponse queryWeekendMealIsAllowedPeriod();
=======
    Boolean queryWeekendMealIsAllowedPeriod();
    List<UUID> queryUserIdByStatus(WeekendMealApplicationStatus status);
>>>>>>> ♻️ :: 특정 유저들에게 알림보내도록 수정
}
