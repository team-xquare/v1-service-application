package io.github.v1serviceapplication.picnicdatetime;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class PicnicTime {
    private final UUID id;
    private final LocalTime picnicTime;
    private final TimeType timeType;
}
