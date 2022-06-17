package io.github.v1serviceapplication.weekendmeal.queryweekendmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryWeekendMealResponse {

    private final String title;
    private final boolean applied;

}
