package io.github.v1serviceapplication.stay.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.code.CodeElement;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusCodeResponse;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.exception.AlreadyExistsStayException;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class StayApiImpl implements StayApi {
    private final StayRepositorySpi stayRepositorySpi;
    private final UserIdFacade userIdFacade;

    @Override
    public void setDefaultStay(UUID userId) {
        if (stayRepositorySpi.existsByUserId(userId)) {
            throw AlreadyExistsStayException.EXCEPTION;
        }
        stayRepositorySpi.applyStay(userId, StayStatusCode.STAY);
    }

    @Override
    public void deleteStay(UUID userId) {
        stayRepositorySpi.deleteStay(userId);
    }

    @Override
    public QueryStayStatusCodeResponse queryStayStatusCode() {
        return QueryStayStatusCodeResponse.builder()
                .codes(Arrays.stream(StayStatusCode.values())
                        .map(stayStatus -> CodeElement.builder()
                                .name(stayStatus.name())
                                .value(stayStatus.getValue())
                                .build()
                        ).collect(Collectors.toList())
                ).build();
    }

    @Override
    public QueryStayStatusResponse queryStayStatus() {
        return stayRepositorySpi.queryStayStatus(userIdFacade.getCurrentUserId());
    }

    @Override
    public void applyStay(StayStatusCode status) {
        stayRepositorySpi.applyStay(userIdFacade.getCurrentUserId(), status);
    }
}
