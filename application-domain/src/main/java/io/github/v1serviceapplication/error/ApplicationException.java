package io.github.v1serviceapplication.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
