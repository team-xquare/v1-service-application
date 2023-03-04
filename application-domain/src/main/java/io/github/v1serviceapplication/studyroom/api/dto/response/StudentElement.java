package io.github.v1serviceapplication.studyroom.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentElement{

    private final String studentName;
    private final int grade;
    private final int classNum;
    private final int num;
    private final String profileImage;

}
