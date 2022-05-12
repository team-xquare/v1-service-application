package io.github.v1serviceapplication.picnicapply;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class PicnicApply {
    private UUID userId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String reason;
    private String arrangement;
}
