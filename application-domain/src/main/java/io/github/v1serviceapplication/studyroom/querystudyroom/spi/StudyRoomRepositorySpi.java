package io.github.v1serviceapplication.studyroom.querystudyroom.spi;

import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;

import java.util.List;

public interface StudyRoomRepositorySpi {
    List<StudyRoomModel> findAll();
}
