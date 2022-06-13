package io.github.v1serviceapplication.domain.studyroom.exception;

import io.github.v1serviceapplication.error.ApplicationException;
import io.github.v1serviceapplication.error.ErrorCode;

public class StudyRoomNotFoundException extends ApplicationException {

    private StudyRoomNotFoundException() {
        super(ErrorCode.STUDY_ROOM_NOT_FOUND);
    }

    public static final ApplicationException EXCEPTION = new StudyRoomNotFoundException();

}
