package io.github.v1serviceapplication.picnic.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
public class StudentPicnicDetail {
    private final LocalTime startTime;

    private final LocalTime endTime;

    private final String reason;

    private final String arrangement;

    private final LocalDateTime createDateTime;
}
