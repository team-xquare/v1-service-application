package io.github.v1serviceapplication.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorAttribute {
    UNEXPECTED_ERROR(500, "Unexpected Error"),
    BAD_REQUEST(400, "Bad Request"),
    REQUEST_NOT_FOUND(404, "No Controller Mapped"),
    CONVERT_FAILED(404, "The code value convert failed"),
    JWT_SIGNATURE(401, "Jwt Signature is not valid"),
    JWT_EXPIRED(401, "Jwt Token Expired");


    private final int status;
    private final String message;
}
