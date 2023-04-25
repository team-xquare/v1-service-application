package io.github.v1serviceapplication.picnicdatetime;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class PicnicDateTime {
    private final UUID id;
    private final LocalDateTime picnicDateTime;
    private final DateTimeType dateTimeType;
}
