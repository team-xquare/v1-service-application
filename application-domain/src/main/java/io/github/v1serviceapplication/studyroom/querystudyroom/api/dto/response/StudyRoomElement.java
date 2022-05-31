package io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class StudyRoomElement{

    private final UUID id;

    private final String studyRoomName;

    private final Integer applicationCount;

    private final List<StudentElement> students;
    
}
