package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.global.error.GlobalErrorCode;

public class AttributeConvertFailedException extends GlobalException {

    public static final GlobalException EXCEPTION = new AttributeConvertFailedException();

    private AttributeConvertFailedException() {
        super(GlobalErrorCode.CONVERT_FAILED);
    }
}