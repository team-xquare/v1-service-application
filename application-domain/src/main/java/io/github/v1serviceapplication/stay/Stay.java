package io.github.v1serviceapplication.stay;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.util.UUID;

@Aggregate
@Getter
@Builder
public class Stay {
    private UUID userId;
    private String code;
}
