package io.github.v1serviceapplication.domain.studyroom.presentation.dto.response;

import io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response.StudyRoomElement;

import java.util.List;

public record StudyRoomList(
        List<StudyRoomElement> studyRooms
) {

    public List<StudyRoomElement> getStudyRooms() {
        return studyRooms;
    }

}
