package io.github.v1serviceapplication.weekendmeal.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class NonResponseStatusIsImpossibleException extends ApplicationException {
    private NonResponseStatusIsImpossibleException() {
        super(ErrorCode.NON_RESPONSE_STATUS_IS_IMPOSSIBLE);
    }
    public static final ApplicationException EXCEPTION = new NonResponseStatusIsImpossibleException();
}
