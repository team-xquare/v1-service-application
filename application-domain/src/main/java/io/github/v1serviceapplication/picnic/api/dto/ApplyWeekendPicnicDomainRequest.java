package io.github.v1serviceapplication.picnic.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class ApplyWeekendPicnicDomainRequest {
    private LocalTime start_time;

    private LocalTime end_time;

    private String reason;

    private String arrangement;
}
