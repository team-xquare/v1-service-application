package io.github.v1serviceapplication.weekendmeal.queryweekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.api.QueryWeekendMeal;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.spi.QueryWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.spi.QueryWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class QueryWeekendMealImpl implements QueryWeekendMeal {

    private final QueryWeekendMealRepositorySpi queryWeekendMealRepositorySpi;
    private final QueryWeekendMealApplyRepositorySpi queryWeekendMealApplyRepositorySpi;

    @Override
    public QueryWeekendMealResponse queryWeekendMeal() {
        UUID userId = UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398");

        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        boolean applied = queryWeekendMealApplyRepositorySpi
                .queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(userId, weekendMeal.getId());

        return new QueryWeekendMealResponse(
                weekendMeal.getTitle(),
                applied
        );

    }

}
