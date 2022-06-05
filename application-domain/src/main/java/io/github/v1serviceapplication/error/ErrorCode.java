package io.github.v1serviceapplication.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {


    FULL_STUDY_ROOM(400, "STUDY-ROOM-400-1", "Full Study Room."),

    ALREADY_JOIN_STUDY_ROOM(409, "STUDY-ROOM-409-1", "Already Join Study Room."),

    INTERNAL_SERVER_ERROR(500, "APPLICATION-500-1",  "Internal Server Error.");

    private final int status;
    private final String code;
    private final String message;

}
