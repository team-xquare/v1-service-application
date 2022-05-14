package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.global.error.GlobalErrorCode;

public class NotFoundException extends GlobalException {

    public static final GlobalException EXCEPTION = new NotFoundException();

    private NotFoundException() {
        super(GlobalErrorCode.REQUEST_NOT_FOUND);
    }
}