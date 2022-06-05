package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class ConvertFailedException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new ConvertFailedException();

    private ConvertFailedException() {
        super(ErrorCode.CONVERT_FAILED);
    }

}
