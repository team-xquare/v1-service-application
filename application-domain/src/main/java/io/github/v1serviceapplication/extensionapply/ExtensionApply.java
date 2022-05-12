package io.github.v1serviceapplication.extensionapply;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Aggregate
public class ExtensionApply {
    private UUID userId;
    private UUID studyRoomId;
}
