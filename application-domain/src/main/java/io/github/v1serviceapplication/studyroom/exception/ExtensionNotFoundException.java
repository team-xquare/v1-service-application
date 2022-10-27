package io.github.v1serviceapplication.studyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class ExtensionNotFoundException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new ExtensionNotFoundException();

    private ExtensionNotFoundException() {
        super(ErrorCode.EXTENSION_NOT_FOUND);
    }

}
