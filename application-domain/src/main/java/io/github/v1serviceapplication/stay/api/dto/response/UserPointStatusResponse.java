package io.github.v1serviceapplication.stay.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPointStatusResponse {
    private final Integer goodPoint;
    private final Integer badPoint;
}
