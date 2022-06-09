package io.github.v1serviceapplication.stay.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class QueryStayStatusCodeResponse {
    private final List<Code> codes;

    @Builder
    @Getter
    public static class Code {

        private final String name;

        private final String value;
    }
}
