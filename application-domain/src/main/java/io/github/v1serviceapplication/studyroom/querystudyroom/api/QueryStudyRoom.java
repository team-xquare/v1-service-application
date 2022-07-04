package io.github.v1serviceapplication.studyroom.querystudyroom.api;

import io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response.StudyRoomElement;

import java.util.List;

public interface QueryStudyRoom {
    List<StudyRoomElement> queryStudyRooms();
}
