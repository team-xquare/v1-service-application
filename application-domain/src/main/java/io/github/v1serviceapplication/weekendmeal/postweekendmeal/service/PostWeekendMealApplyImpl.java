package io.github.v1serviceapplication.weekendmeal.postweekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.api.PostWeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.spi.QueryWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class PostWeekendMealApplyImpl implements PostWeekendMealApply {

    private final PostWeekendMealApplyRepositorySpi postWeekendMealApplyRepositorySpi;
    private final QueryWeekendMealRepositorySpi queryWeekendMealRepositorySpi;

    @Override
    public void postWeekendMealApply(boolean apply, UUID userId) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        saveOrUpdate(userId, weekendMeal.getId(), apply);
    }

    private void saveOrUpdate(UUID userId, UUID weekendMealId, boolean apply) {
        if (postWeekendMealApplyRepositorySpi.currentWeekendMealApplyExist(userId, weekendMealId)) {
            postWeekendMealApplyRepositorySpi.updateWeekendMealApply(userId, weekendMealId, apply);
        } else {
            postWeekendMealApplyRepositorySpi.saveWeekendMealApply(
                    WeekendMealApply.builder()
                            .userId(userId)
                            .weekendMealId(weekendMealId)
                            .isApplied(apply)
                            .build()
            );
        }
    }

}
