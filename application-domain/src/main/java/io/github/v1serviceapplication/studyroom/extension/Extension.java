package io.github.v1serviceapplication.studyroom.extension;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Aggregate
public class Extension {
    private final UUID id;
    private final UUID userId;
    private final UUID studyRoomId;
}
