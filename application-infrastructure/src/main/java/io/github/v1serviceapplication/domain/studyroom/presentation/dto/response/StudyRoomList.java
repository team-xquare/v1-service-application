package io.github.v1serviceapplication.domain.studyroom.presentation.dto.response;

import io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response.StudyRoomElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StudyRoomList{

    private final List<StudyRoomElement> studyRooms;

}
