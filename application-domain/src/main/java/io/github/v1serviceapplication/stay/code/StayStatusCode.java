package io.github.v1serviceapplication.stay.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StayStatusCode {
    STAY("AP0001", "잔류"),
    FRIDAY_RETURN_HOME("AP0002", "금요 귀가"),
    SATURDAY_RETURN_HOME("AP0003", "토요 귀가"),
    SATURDAY_RETURN_DORM("AP0004", "토요 귀사");

    private final String code;
    private final String value;
}
