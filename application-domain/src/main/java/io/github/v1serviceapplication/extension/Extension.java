package io.github.v1serviceapplication.extension;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Aggregate
public class Extension {
    private UUID userId;
    private UUID studyRoomId;
}