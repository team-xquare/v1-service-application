package io.github.v1serviceapplication.stay.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.stay.api.QueryStayStatus;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class QueryStayStatusImpl implements QueryStayStatus {
    private final StayRepositorySpi stayRepositorySpi;

    @Override
    public QueryStayStatusResponse queryStayStatus() {
        return stayRepositorySpi.queryStayStatus();
    }
}
