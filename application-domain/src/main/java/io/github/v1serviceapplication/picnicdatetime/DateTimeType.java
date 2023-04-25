package io.github.v1serviceapplication.picnicdatetime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public enum DateTimeType {
    PICNIC_REQUEST_START_TIME(LocalTime.of(20, 30)),
    PICNIC_REQUEST_END_TIME(LocalTime.of(11, 30)),
    PICNIC_ALLOW_START_TIME(LocalTime.of(12, 0)),
    PICNIC_ALLOW_END_TIME(LocalTime.of(20, 0));

    private LocalTime value;
}
