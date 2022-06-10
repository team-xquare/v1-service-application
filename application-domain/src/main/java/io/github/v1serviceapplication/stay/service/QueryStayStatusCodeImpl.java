package io.github.v1serviceapplication.stay.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.code.CodeElement;
import io.github.v1serviceapplication.stay.api.QueryStayStatusCode;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusCodeResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;

import java.util.Arrays;
import java.util.stream.Collectors;

@DomainService
public class QueryStayStatusCodeImpl implements QueryStayStatusCode {
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
}
