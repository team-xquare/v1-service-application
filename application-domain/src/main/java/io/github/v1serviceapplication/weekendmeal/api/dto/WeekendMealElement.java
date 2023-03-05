package io.github.v1serviceapplication.weekendmeal.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class WeekendMealElement {
    private final UUID id;
    private final String name;
    private final String num;
}
