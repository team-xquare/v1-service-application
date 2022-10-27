package io.github.v1serviceapplication.studyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class InCorrectStudyRoomIdException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new InCorrectStudyRoomIdException();

    public InCorrectStudyRoomIdException() {
        super(ErrorCode.IN_CORRECT_STUDY_ROOM_ID);
    }

}
