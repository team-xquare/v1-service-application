package io.github.v1serviceapplication.infrastructure.feign;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.v1serviceapplication.infrastructure.feign.error.FeignBadRequestException;
import io.github.v1serviceapplication.infrastructure.feign.error.FeignExpiredTokenException;
import io.github.v1serviceapplication.infrastructure.feign.error.FeignForbiddenException;
import io.github.v1serviceapplication.infrastructure.feign.error.FeignUnAuthorizedException;

public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400) {
            switch (response.status()) {
                case 401 -> throw FeignUnAuthorizedException.EXCEPTION;
                case 403 -> throw FeignForbiddenException.EXCEPTION;
                case 419 -> throw FeignExpiredTokenException.EXCEPTION;
                default -> throw FeignBadRequestException.EXCEPTION;
            }
        }

        return FeignException.errorStatus(methodKey, response);
    }

}
