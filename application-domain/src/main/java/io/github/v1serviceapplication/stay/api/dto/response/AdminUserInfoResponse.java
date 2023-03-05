package io.github.v1serviceapplication.stay.api.dto.response;

import io.github.v1serviceapplication.stay.code.StayStatusCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserInfoResponse {
    private final String username;
    private final String num;
    private final Integer goodPoint;
    private final Integer badPoint;
    private final String code;
}
