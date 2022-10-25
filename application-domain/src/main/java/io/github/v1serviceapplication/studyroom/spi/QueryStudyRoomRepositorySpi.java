package io.github.v1serviceapplication.studyroom.spi;

import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.spi.dto.StudyRoomModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueryStudyRoomRepositorySpi {
    List<StudyRoomModel> findAll();
    Optional<StudyRoom> findStudyRoomIdByUserId(UUID userId);
}
