package io.github.v1serviceapplication.infrastructure.feign.error;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class FeignUnAuthorizedException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new FeignUnAuthorizedException();

    public FeignUnAuthorizedException() {
        super(ErrorCode.FEIGN_UNAUTHORIZED);
    }

}
