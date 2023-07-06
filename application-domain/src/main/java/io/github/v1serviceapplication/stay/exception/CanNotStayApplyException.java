package io.github.v1serviceapplication.stay.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class CanNotStayApplyException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new CanNotStayApplyException();

    private CanNotStayApplyException() {
        super(ErrorCode.CAN_NOT_STAY_APPLY);
    }
}
