package io.github.v1serviceapplication.weekendmeal;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class WeekendMealApply {
    private final UUID id;
    private final UUID userId;
    private final Boolean isApplied;
    private final LocalDate date;
}
