package io.github.v1serviceapplication.stay.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryAllStayStatusElement {
    private final String num;
    private final String name;
    private final String code;
}
