package io.github.v1serviceapplication.weekendmeal.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class WeekendMealNotFoundException extends ApplicationException {

    private WeekendMealNotFoundException() {
        super(ErrorCode.WEEKEND_MEAL_NOT_FOUND);
    }

    public static final ApplicationException EXCEPTION = new WeekendMealNotFoundException();

}
