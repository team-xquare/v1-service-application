package io.github.v1serviceapplication.domain.picnic.presentation.dto.request;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class UpdatePicnicRequest {
    private LocalTime startTime;

    private LocalTime endTime;

    private String reason;

    private String arrangement;
}
