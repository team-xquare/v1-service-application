package io.github.v1serviceapplication.stay.api;

import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;

import java.util.UUID;

public interface QueryStayStatus {
    QueryStayStatusResponse queryStayStatus(UUID userId);
}
