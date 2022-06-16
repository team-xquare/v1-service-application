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

    private PostWeekendMealApplyRepositorySpi postWeekendMealApplyRepositorySpi;
    private QueryWeekendMealRepositorySpi queryWeekendMealRepositorySpi;

    @Override
    public void postWeekendMealApply(boolean apply) {
        UUID userId = UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398"); //TODO userId 가져오기

        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        if (postWeekendMealApplyRepositorySpi.todayWeekendMealApplyExist(userId)) {
            postWeekendMealApplyRepositorySpi.updateWeekendMealApply(userId, apply);
        } else {
            postWeekendMealApplyRepositorySpi.saveWeekendMealApply(
                    WeekendMealApply.builder()
                            .userId(userId)
                            .weekendMealId(weekendMeal.getId())
                            .isApplied(apply)
                            .build()
            );
        }
    }
}
