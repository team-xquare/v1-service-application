package io.github.v1serviceapplication.weekendmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class WeekendMealUserInfoElement {
    private final UUID userId;
    private final String userName;
    private final int grade;
    private final int classNum;
}
