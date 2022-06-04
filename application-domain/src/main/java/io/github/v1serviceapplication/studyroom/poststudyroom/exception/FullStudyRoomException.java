package io.github.v1serviceapplication.studyroom.poststudyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class FullStudyRoomException extends ApplicationException {

    private FullStudyRoomException() {
        super(ErrorCode.FULL_STUDY_ROOM);
    }

    public static final ApplicationException EXCEPTION = new FullStudyRoomException();

}
