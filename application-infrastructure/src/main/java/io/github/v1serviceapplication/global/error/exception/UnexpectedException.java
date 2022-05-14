package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.global.error.GlobalErrorCode;

public class UnexpectedException extends GlobalException {

    public static final GlobalException EXCEPTION = new UnexpectedException();

    private UnexpectedException() {
        super(GlobalErrorCode.UNEXPECTED_ERROR);
    }
}
