package io.github.v1serviceapplication.studyroom.querystudyroom.spi;

import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;

import java.util.List;
import java.util.UUID;

public interface StudyRoomRepositorySpi {
    List<StudyRoomModel> findAll();
    UUID findStudyRoomIdByUserId(UUID userId);
}
