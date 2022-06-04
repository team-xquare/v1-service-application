package io.github.v1serviceapplication.studyroom.poststudyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class AlreadyJoinStudyRoomException extends ApplicationException {

    private AlreadyJoinStudyRoomException() {
        super(ErrorCode.ALREADY_JOIN_STUDY_ROOM);
    }

    public static final ApplicationException EXCEPTION = new AlreadyJoinStudyRoomException();

}
