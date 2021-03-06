package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class JwtTokenExpiredException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new JwtTokenExpiredException();

    private JwtTokenExpiredException() {
        super(ErrorCode.JWT_EXPIRED);
    }
}
