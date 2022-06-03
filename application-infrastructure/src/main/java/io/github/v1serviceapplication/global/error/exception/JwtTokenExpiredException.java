package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.global.error.GlobalErrorCode;

public class JwtTokenExpiredException extends GlobalException{
    public static final GlobalException EXCEPTION = new JwtTokenExpiredException();

    private JwtTokenExpiredException() {
        super(GlobalErrorCode.JWT_EXPIRED);
    }
}
