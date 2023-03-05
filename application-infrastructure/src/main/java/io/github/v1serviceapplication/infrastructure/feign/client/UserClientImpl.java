package io.github.v1serviceapplication.infrastructure.feign.client;

import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.spi.PicnicUserFeignSpi;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserClientImpl implements StudyRoomUserFeignSpi, PicnicUserFeignSpi {

    private final UserClient userClient;

    @Override
    public List<StudentElement> queryUserInfoByUserId(List<UUID> userId) {
        if (userId.isEmpty())
            return null;

        return userClient.queryUserInfoByUserId(userId).getUsers()
                .stream().map(
                        user -> new StudentElement(user.getName(), user.getProfileFileName())
                ).collect(Collectors.toList());
    }

    @Override
    public List<PicnicUserElement> getUserInfoByUserId(List<UUID> userId) {
        if (userId.isEmpty()) {
            return Collections.emptyList();
        }
        return userClient.queryUserInfoByUserId(userId)
                .getUsers()
                .stream()
                .map(
                        user -> new PicnicUserElement(
                                user.getId(),
                                user.getGrade().toString() + user.getClassNum().toString() + String.format("%02d", user.getNum()),
                                user.getName()
                        )
                ).toList();
    }
}
