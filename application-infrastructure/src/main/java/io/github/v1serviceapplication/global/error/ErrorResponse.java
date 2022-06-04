package io.github.v1serviceapplication.global.error;

import io.github.v1serviceapplication.error.ErrorCode;
import lombok.*;

@Getter
public class ErrorResponse {

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private int status;

    private String code;

    private String message;

    @Override
    public String toString() {
        return String.format("""
                {
                    "status": %s,
                    "code": %s,
                    "message": "%s"
                }
                """, status, code, message);
    }
}