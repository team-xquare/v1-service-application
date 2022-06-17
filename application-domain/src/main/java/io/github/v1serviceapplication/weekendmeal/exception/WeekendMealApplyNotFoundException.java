package io.github.v1serviceapplication.weekendmeal.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class WeekendMealApplyNotFoundException extends ApplicationException {

    private WeekendMealApplyNotFoundException() {
        super(ErrorCode.WEEKEND_MEAL_APPLY_NOT_FOUND);
    }

    public static final ApplicationException EXCEPTION = new WeekendMealApplyNotFoundException();

}
