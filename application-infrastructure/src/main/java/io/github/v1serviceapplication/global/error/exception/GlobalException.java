package io.github.v1serviceapplication.global.error.exception;

import io.github.v1serviceapplication.global.error.ErrorAttribute;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ErrorAttribute errorAttribute;

    public GlobalException(ErrorAttribute errorAttribute) {
        super(errorAttribute.getMessage());
        this.errorAttribute = errorAttribute;
    }

}