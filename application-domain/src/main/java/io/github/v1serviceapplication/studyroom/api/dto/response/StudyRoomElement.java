package io.github.v1serviceapplication.studyroom.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class StudyRoomElement{

    private final UUID id;

    private final String studyRoomName;

    private final Integer applicationCount;

    private final Integer maxPeopleCount;

    private final List<StudentElement> students;
    
}
