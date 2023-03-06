package io.github.v1serviceapplication.picnic;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class Picnic {
    private final UUID id;
    private final UUID userId;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String reason;
    private final String arrangement;
    private final LocalTime dormitoryReturnCheckTime;
    private final Boolean isAcceptance;

}
