package io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response;

import java.util.List;
import java.util.UUID;

public record StudyRoomElement(
        UUID id,
        String studyRoomName,
        Integer applicationCount,

        List<StudentElement> students
) {

    public UUID getId() {
        return this.id;
    }

    public String getStudyRoomName() {
        return this.studyRoomName;
    }

    public Integer getApplicationCount() {
        return this.applicationCount;
    }

    public List<StudentElement> getStudents() {
        return students;
    }

}
