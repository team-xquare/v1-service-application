package io.github.v1serviceapplication.infrastructure.feign.error;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class FeignForbiddenException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new FeignForbiddenException();

    public FeignForbiddenException() {
        super(ErrorCode.FEIGN_FORBIDDEN);
    }

}
