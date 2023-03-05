package io.github.v1serviceapplication.picnic.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
public class PicnicElement {
    private final UUID id;
    private final UUID userId;
    private final String num;
    private final String name;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Boolean isAcceptance;
}
