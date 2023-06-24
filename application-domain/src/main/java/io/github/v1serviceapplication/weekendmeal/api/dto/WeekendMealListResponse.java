package io.github.v1serviceapplication.weekendmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WeekendMealListResponse {
    private final List<WeekendMealElement> responseStudents;
    private final List<WeekendMealElement> nonResponseStudents;
}
