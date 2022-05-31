package io.github.v1serviceapplication.studyroom;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.*;

import java.util.UUID;

@Aggregate
@Getter
@Builder
public class StudyRoom {

    public StudyRoom(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    private UUID id;
    private String name;
}
