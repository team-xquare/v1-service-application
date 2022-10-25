package io.github.v1serviceapplication.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {


    FULL_STUDY_ROOM(400, "STUDY-ROOM-400-1", "Full Study Room."),

    JWT_SIGNATURE(401, "APPLICATION-401-1", "Jwt Signature is not valid."),

    JWT_EXPIRED(401, "APPLICATION-401-2", "Jwt Token Expired."),

    CONVERT_FAILED(404, "APPLICATION-404-1", "The code value convert failed."),

    STUDY_ROOM_NOT_FOUND(404, "STUDY-ROOM-404-1", "StudyRoom Not Found."),

    EXTENSION_NOT_FOUND(404, "EXTENSION-404-1", "Extension Not Found."),

    ALREADY_EXISTS_STAY(400, "STAY-400-1", "Already Exists Stay."),

    STAY_NOT_FOUND(404, "STAY-404-1", "Stay Not Found."),

    WEEKEND_MEAL_NOT_FOUND(404, "WEEKEND-404-1", "Weekend Meal Not Found."),

    WEEKEND_MEAL_APPLY_NOT_FOUND(404, "WEEKEND-APPLY-404-1", "Weekend Meal Apply Not Found."),

    INTERNAL_SERVER_ERROR(500, "APPLICATION-500-1",  "Internal Server Error."),

    FEIGN_BAD_REQUEST(400, "FEIGN-400-1", "Feign Bad Request."),

    FEIGN_UNAUTHORIZED(401, "FEIGN-401-1", "Feign Unauthorized."),

    FEIGN_FORBIDDEN(403, "FEIGN-403-1", "Feign Forbidden."),

    FEIGN_EXPIRED_TOKEN(419, "FEIGN-419-1", "Feign Expired Token.");

    private final int status;
    private final String code;
    private final String message;

}
