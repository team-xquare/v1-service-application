package io.github.v1serviceapplication.weekendmeakcheck;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class WeekendMealCheck {
    private final UUID id;
    private final UUID userId;
    private final LocalDate createDate;
    private final UUID weekendMealId;
    private final boolean isCheck;
    private final int grade;
    private final int classNum;
}
