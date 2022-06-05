package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class InternalServerErrorException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new InternalServerErrorException();

    private InternalServerErrorException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
