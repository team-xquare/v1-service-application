package io.github.v1serviceapplication.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    FULL_STUDY_ROOM(400, "STUDY-ROOM-400-1", "Full Study Room."),

    INVALID_STUDY_ROOM_APPLICATION_TIME(400, "STUDY-ROOM-400-2", "Invalid Study Room Application Time."),

    JWT_SIGNATURE(401, "APPLICATION-401-1", "Jwt Signature is not valid."),

    JWT_EXPIRED(401, "APPLICATION-401-2", "Jwt Token Expired."),

    CONVERT_FAILED(404, "APPLICATION-404-1", "The code value convert failed."),

    STUDY_ROOM_NOT_FOUND(404, "STUDY-ROOM-404-1", "StudyRoom Not Found."),

    EXTENSION_NOT_FOUND(404, "EXTENSION-404-1", "Extension Not Found."),

    IN_CORRECT_STUDY_ROOM_ID(400, "EXTENSION-400-1", "In Correct StudyRoom ID"),

    ALREADY_EXISTS_STAY(400, "STAY-400-1", "Already Exists Stay."),

    STAY_NOT_FOUND(404, "STAY-404-1", "Stay Not Found."),

    PICNIC_NOT_FOUND(404, "STAY-404-1", "Picnic Not Found."),
    
    WEEKEND_MEAL_NOT_FOUND(404, "WEEKEND-404-1", "Weekend Meal Not Found."),

    WEEKEND_MEAL_APPLY_NOT_FOUND(404, "WEEKEND-APPLY-404-1", "Weekend Meal Apply Not Found."),

    INTERNAL_SERVER_ERROR(500, "APPLICATION-500-1", "Internal Server Error."),

    FEIGN_BAD_REQUEST(400, "FEIGN-400-1", "Feign Bad Request."),

    FEIGN_UNAUTHORIZED(401, "FEIGN-401-1", "Feign Unauthorized."),

    FEIGN_FORBIDDEN(403, "FEIGN-403-1", "Feign Forbidden."),

    FEIGN_EXPIRED_TOKEN(419, "FEIGN-419-1", "Feign Expired Token."),

    INVALID_STAY_STATUS(401, "STAY-401-1", "Invalid Stay Status."),

    USER_NOT_EMPTY(409, "PICNIC-409-1", "User Not Empty."),

    USER_ID_NOT_FOUND(404, "PICNIC-404-1", "Not Found User Id."),

    INVALID_PICNIC_TIME(400, "PICNIC-400-1", "Picnic Time Invalid."),

    PICNIC_RESERVATION_NOT_FOUND(404, "RESERVATION-404-1", "Picnic Reservation Not Found."),

    PICNIC_RESERVE_NOT_AVAILABLE(400, "RESERVATION-403-1", "Picnic Reserve Not Available."),

    PICNIC_APPLY_NOT_AVAILABLE(400, "PICNIC-403-1", "Picnic Apply Not Available."),

    PICNIC_PASS_NOT_MODIFY(403, "PICNIC-403-2", "Picnic Pass Not Modify.");

    private final int status;
    private final String code;
    private final String message;

}
