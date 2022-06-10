package io.github.v1serviceapplication.picnic.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class ApplyWeekendPicnicDomainRequest {
    private LocalTime startTime;

    private LocalTime endTime;

    private String reason;

    private String arrangement;
}
