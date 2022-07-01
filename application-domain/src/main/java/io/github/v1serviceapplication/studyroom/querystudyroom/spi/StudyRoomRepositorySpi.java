package io.github.v1serviceapplication.studyroom.querystudyroom.spi;

import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudyRoomRepositorySpi {
    List<StudyRoomModel> findAllByFloorIn(List<Integer> floorList);
    Optional<StudyRoom> findStudyRoomIdByUserId(UUID userId);
}
