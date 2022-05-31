package io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class StudyRoomModel {

    UUID id;

    String name;

    Integer applicationCount;

    List<UUID> studentList;

}
