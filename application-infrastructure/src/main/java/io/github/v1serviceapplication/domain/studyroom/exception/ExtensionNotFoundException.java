package io.github.v1serviceapplication.domain.studyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class ExtensionNotFoundException extends ApplicationException {

    private ExtensionNotFoundException() {
        super(ErrorCode.EXTENSION_NOT_FOUND);
    }

    public static final ApplicationException EXCEPTION = new ExtensionNotFoundException();

}
