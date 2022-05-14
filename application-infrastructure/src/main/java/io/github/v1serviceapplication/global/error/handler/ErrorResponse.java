package io.github.v1serviceapplication.global.error.handler;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ErrorResponse {

    private int status;

    private String message;

    @Override
    public String toString() {
        return String.format("""
                {
                    "status": %s,
                    "message": "%s"
                }
                """, status, message);
    }
}