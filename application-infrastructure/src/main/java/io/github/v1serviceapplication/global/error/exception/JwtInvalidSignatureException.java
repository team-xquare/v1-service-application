package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.global.error.GlobalErrorCode;

public class JwtInvalidSignatureException extends GlobalException{
    public static final GlobalException EXCEPTION = new JwtInvalidSignatureException();

    private JwtInvalidSignatureException() {
        super(GlobalErrorCode.JWT_SIGNATURE);
    }
}
