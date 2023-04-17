package io.github.v1serviceapplication.studyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class InvalidStudyRoomApplicationTimeException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new InvalidStudyRoomApplicationTimeException();

    private InvalidStudyRoomApplicationTimeException() {
        super(ErrorCode.INVALID_STUDY_ROOM_APPLICATION_TIME);
    }
}
