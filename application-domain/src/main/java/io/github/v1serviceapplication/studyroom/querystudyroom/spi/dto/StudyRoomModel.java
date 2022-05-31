package io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto;

import java.util.List;
import java.util.UUID;

public record StudyRoomModel(UUID id, String name, Integer applicationCount,
                             List<UUID> studentList) {

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getApplicationCount() {
        return applicationCount;
    }

    public List<UUID> getStudentList() {
        return studentList;
    }

}
