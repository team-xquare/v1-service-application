package io.github.v1serviceapplication.stay.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryStayStatusResponse {
    private final String status;
}
