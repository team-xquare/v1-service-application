package io.github.v1serviceapplication.studyroom.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class StudentElement{

    private final UUID userId;
    private final String studentName;
    private final int grade;
    private final int classNum;
    private final int num;
    private final String profileImage;

}
