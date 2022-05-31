package io.github.v1serviceapplication.weekendmeal;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.util.UUID;

@Aggregate
@Getter
@Builder
public class WeekendMeal {
    private UUID userId;
    private Boolean isApplied;
}
