package io.github.v1serviceapplication.stay.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class QueryAllStayStatusResponse {
    private final List<QueryAllStayStatusElement> stayList;
}
