package io.github.v1serviceapplication.stay.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class AlreadyExistsStayException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new AlreadyExistsStayException();

    private AlreadyExistsStayException() {
        super(ErrorCode.ALREADY_EXISTS_STAY);
    }

}
