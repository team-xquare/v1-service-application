package io.github.v1serviceapplication.weekendmeal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeekendMealApplicationStatus {
    APPLY("신청"),
    NOT_APPLY("미신청"),
    NON_RESPONSE("미응답");

    private final String value;
}
