package io.github.v1serviceapplication.studyroom.spi;

import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;

import java.util.List;
import java.util.UUID;

public interface StudyRoomUserFeignSpi {
    List<StudentElement> queryUserInfoByUserId(List<UUID> userId);
}
