package io.github.v1serviceapplication.stay.api;

import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusCodeResponse;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;

import java.util.UUID;

public interface StayApi {
    void setDefaultStay();
    void deleteStay(UUID userId);
    QueryStayStatusCodeResponse queryStayStatusCode();
    QueryStayStatusResponse queryStayStatus();
    void applyStay(StayStatusCode status);
}
