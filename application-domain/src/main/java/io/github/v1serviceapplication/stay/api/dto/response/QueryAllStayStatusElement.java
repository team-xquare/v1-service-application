package io.github.v1serviceapplication.stay.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class QueryAllStayStatusElement {
    private final UUID id;
    private final String num;
    private final String name;
    private final String code;
}
