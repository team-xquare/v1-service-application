package io.github.v1serviceapplication.stayapply;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.util.UUID;

@Aggregate
@Getter
@Builder
public class StayApply {
    private UUID userId;
    private String code;
}
