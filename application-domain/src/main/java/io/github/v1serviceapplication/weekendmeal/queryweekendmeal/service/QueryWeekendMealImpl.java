package io.github.v1serviceapplication.weekendmeal.queryweekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.api.QueryWeekendMeal;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.spi.QueryWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class QueryWeekendMealImpl implements QueryWeekendMeal {

    private final QueryWeekendMealRepositorySpi queryWeekendMealRepositorySpi;

    @Override
    public String queryWeekendMeal() {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if(weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        return weekendMeal.getTitle();

    }

}
