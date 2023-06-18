package io.github.v1serviceapplication.weekendmeal.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class WeekendMealUserElement {
    private final UUID userId;
    private final String num;
    private final String name;
}
