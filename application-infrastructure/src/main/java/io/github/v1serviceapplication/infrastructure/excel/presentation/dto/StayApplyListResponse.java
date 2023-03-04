package io.github.v1serviceapplication.infrastructure.excel.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StayApplyListResponse {
    private final List<StayStatus> students;
}
