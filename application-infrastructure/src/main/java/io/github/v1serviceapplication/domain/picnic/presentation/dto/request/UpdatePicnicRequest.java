package io.github.v1serviceapplication.domain.picnic.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class UpdatePicnicRequest {
    private LocalTime startTime;

    private LocalTime endTime;

    private String reason;

    private String arrangement;
}
