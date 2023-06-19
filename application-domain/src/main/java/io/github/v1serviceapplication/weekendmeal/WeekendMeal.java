package io.github.v1serviceapplication.weekendmeal;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class WeekendMeal {
    private UUID id;
    private String title;
    private LocalDate date;
    private boolean allowedPeriod;
}
