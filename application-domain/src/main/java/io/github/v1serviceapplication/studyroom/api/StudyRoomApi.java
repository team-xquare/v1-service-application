package io.github.v1serviceapplication.studyroom.api;

import io.github.v1serviceapplication.studyroom.api.dto.response.StudyRoomElement;

import java.util.List;
import java.util.UUID;

public interface StudyRoomApi {
    void postStudyRoom(UUID studyRoomId, UUID userId);
    List<StudyRoomElement> queryStudyRooms();
    UUID queryStudyRoomStatus(UUID userId);
}
