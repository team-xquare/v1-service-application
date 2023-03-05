package io.github.v1serviceapplication.weekendmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class WeekendMealUserElement {
    private final UUID userId;
    private final String num;
    private final String name;
}
