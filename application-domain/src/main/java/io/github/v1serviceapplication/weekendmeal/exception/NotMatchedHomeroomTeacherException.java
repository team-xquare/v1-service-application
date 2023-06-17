package io.github.v1serviceapplication.weekendmeal.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class NotMatchedHomeroomTeacherException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new NotMatchedHomeroomTeacherException();

    private NotMatchedHomeroomTeacherException() {
        super(ErrorCode.NOT_MATCHED_HOMEROOM_TEACHER);
    }
}
