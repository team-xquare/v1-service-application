package io.github.v1serviceapplication.picnic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class PicnicAllowTimeResponse {
    private final LocalTime picnicAllowStartTime;
    private final LocalTime picnicAllowEndTime;
}
