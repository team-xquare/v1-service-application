package io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response;

public record StudentElement(
        String studentName,
        String profileImage
) {

    public String getStudentName() {
        return this.studentName;
    }

    public String getProfileImage() {
        return this.profileImage;
    }

}
