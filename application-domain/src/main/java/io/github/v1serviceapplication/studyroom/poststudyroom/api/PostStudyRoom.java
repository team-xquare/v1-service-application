package io.github.v1serviceapplication.studyroom.poststudyroom.api;

import java.util.UUID;

public interface PostStudyRoom {
    void postStudyRoom(UUID studyRoomId, UUID userId);
}
