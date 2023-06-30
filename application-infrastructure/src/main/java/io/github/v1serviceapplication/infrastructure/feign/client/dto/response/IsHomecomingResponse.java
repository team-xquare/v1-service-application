package io.github.v1serviceapplication.infrastructure.feign.client.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IsHomecomingResponse {
    private final boolean isHomecomingDay;
}
