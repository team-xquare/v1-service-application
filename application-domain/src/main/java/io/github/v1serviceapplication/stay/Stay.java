package io.github.v1serviceapplication.stay;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class Stay {
    private final UUID id;
    private final UUID userId;
    private final String code;
    private final LocalDate createDate;
}
