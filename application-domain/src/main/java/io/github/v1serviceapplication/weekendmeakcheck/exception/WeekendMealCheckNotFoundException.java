package io.github.v1serviceapplication.weekendmeakcheck.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class WeekendMealCheckNotFoundException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new WeekendMealCheckNotFoundException();

    private WeekendMealCheckNotFoundException() {
        super(ErrorCode.WEEKEND_MEAL_CHECK_NOT_FOUND);
    }
}
