package io.github.v1serviceapplication.picnic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
public class WeekendPicnicExcelElement {
    private final UUID userId;
    private final String num;
    private final String name;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String reason;
    private final String arrangement;
    private final Boolean isAcceptance;
}
