package io.github.v1serviceapplication.studyroom.spi;

import io.github.v1serviceapplication.studyroom.StudyRoom;

import java.util.UUID;

public interface PostStudyRoomRepositorySpi {
    boolean todayStudyRoomApplyExist(UUID userId);
    Long applicationCount(UUID studyRoomId);
    StudyRoom findById(UUID studyRoomId);
    void postStudyRoom(UUID studyRoomId, UUID userId);
    void updateStudyRoom(UUID studyRoomId, UUID userId);
}
