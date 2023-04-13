package io.github.v1serviceapplication.studyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class TimeInvalidException extends ApplicationException {

    private TimeInvalidException() {
        super(ErrorCode.TIME_INVALID);
    }

    public static final ApplicationException EXCEPTION = new TimeInvalidException();
}
