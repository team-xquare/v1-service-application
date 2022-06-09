package io.github.v1serviceapplication.stay.api.dto.response;

import io.github.v1serviceapplication.code.CodeElement;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class QueryStayStatusCodeResponse {
    private final List<CodeElement> codes;
}
