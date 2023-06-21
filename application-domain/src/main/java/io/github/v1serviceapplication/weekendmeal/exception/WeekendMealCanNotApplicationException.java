package io.github.v1serviceapplication.weekendmeal.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class WeekendMealCanNotApplicationException extends ApplicationException {
    private WeekendMealCanNotApplicationException() {
        super(ErrorCode.WEEKEND_MEAL_CAN_NOT_APPLICATION);
    }

    public static final ApplicationException EXCEPTION = new WeekendMealCanNotApplicationException();
}
