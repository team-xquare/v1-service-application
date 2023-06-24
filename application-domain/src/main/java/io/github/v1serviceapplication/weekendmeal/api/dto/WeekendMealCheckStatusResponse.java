package io.github.v1serviceapplication.weekendmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeekendMealCheckStatusResponse {
    private final boolean isCheck;
}
