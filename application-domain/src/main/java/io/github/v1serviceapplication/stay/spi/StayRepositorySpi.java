package io.github.v1serviceapplication.stay.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;

import java.util.UUID;

@Spi
public interface StayRepositorySpi {
    void applyStay(StayStatusCode status, UUID userId);
    QueryStayStatusResponse queryStayStatus(UUID userId);
    void setDefaultStay(UUID userId);
    void deleteStay(UUID userId);
}
