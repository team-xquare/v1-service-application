package io.github.v1serviceapplication.infrastructure.feign.client;

import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserClientImpl implements StudyRoomUserFeignSpi {

    private final UserClient userClient;

    @Override
    public List<StudentElement> queryUserInfoByUserId(List<UUID> userId) {
        if (userId.isEmpty()) {
            return null;
        } else {
            return userClient.queryUserInfoByUserId(userId)
                    .getUsers()
                    .stream().map(
                            user -> new StudentElement(
                                    user.getName(), user.getGrade(), user.getClassNum(), user.getNum(), user.getProfileFileName())
                    ).toList();

        }
    }
}
