package io.github.v1serviceapplication.infrastructure.feign.error;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class FeignBadRequestException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new FeignBadRequestException();

    private FeignBadRequestException() {
        super(ErrorCode.FEIGN_BAD_REQUEST);
    }

}
