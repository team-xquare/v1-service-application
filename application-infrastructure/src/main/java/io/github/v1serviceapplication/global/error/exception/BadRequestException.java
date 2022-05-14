package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.global.error.GlobalErrorCode;

public class BadRequestException extends GlobalException {

    public static final GlobalException EXCEPTION = new BadRequestException();

    private BadRequestException() {
        super(GlobalErrorCode.BAD_REQUEST);
    }
}