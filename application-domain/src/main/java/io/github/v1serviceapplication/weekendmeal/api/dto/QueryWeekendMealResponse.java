package io.github.v1serviceapplication.weekendmeal.api.dto;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryWeekendMealResponse {

    private final String title;
    private final WeekendMealApplicationStatus status;

}
