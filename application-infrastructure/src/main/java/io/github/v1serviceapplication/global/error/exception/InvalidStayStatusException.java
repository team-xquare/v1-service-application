package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class InvalidStayStatusException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new InvalidStayStatusException();

    private InvalidStayStatusException() {
        super(ErrorCode.INVALID_STAY_STATUS);
    }
}
