package io.github.v1serviceapplication.weekendmealcheck.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class WeekendMealAlreadyCheckException extends ApplicationException{

    private WeekendMealAlreadyCheckException() {
        super(ErrorCode.WEEKEND_MEAL_APPLY_NOT_FOUND);
    }

    public static final ApplicationException EXCEPTION = new WeekendMealAlreadyCheckException();
}
