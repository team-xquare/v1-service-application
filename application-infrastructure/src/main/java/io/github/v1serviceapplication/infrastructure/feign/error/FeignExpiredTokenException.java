package io.github.v1serviceapplication.infrastructure.feign.error;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class FeignExpiredTokenException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new FeignExpiredTokenException();

    private FeignExpiredTokenException() {
        super(ErrorCode.FEIGN_EXPIRED_TOKEN);
    }

}
