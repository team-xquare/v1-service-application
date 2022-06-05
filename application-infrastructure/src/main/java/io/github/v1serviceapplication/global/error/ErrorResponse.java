package io.github.v1serviceapplication.global.error;

import io.github.v1serviceapplication.error.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private final int status;

    private final String code;

    private final String message;
}