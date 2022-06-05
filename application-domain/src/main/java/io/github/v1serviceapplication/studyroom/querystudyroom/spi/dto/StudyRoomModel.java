package io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class StudyRoomModel {

    private final UUID id;

    private final String name;

    private final Integer applicationCount;

    private final Integer maxPeopleCount;

    private final List<UUID> studentList;

}
