package io.github.v1serviceapplication.weekendmeal.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class NonResponseRequestImpossibleException extends ApplicationException {
    private NonResponseRequestImpossibleException() {
        super(ErrorCode.NON_RESPONSE_REQUEST_IMPOSSIBLE);
    }
    public static final ApplicationException EXCEPTION = new NonResponseRequestImpossibleException();
}
