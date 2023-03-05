package io.github.v1serviceapplication.infrastructure.feign.client.user;

import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponseElement;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.spi.PicnicUserFeignSpi;
import io.github.v1serviceapplication.stay.api.dto.response.StayUserElement;
import io.github.v1serviceapplication.stay.spi.StayUserFeignSpi;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealUserElement;
import io.github.v1serviceapplication.weekendmeal.spi.WeekendMealUserFeignSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserClientImpl implements StudyRoomUserFeignSpi, PicnicUserFeignSpi, StayUserFeignSpi, WeekendMealUserFeignSpi {
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

    @Override
    public List<WeekendMealUserElement> getUserInfoList(List<UUID> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return userClient.queryUserInfoByUserId(ids)
                .getUsers()
                .stream()
                .map(
                        user -> new WeekendMealUserElement(
                                user.getId(),
                                user.getGrade().toString() + user.getClassNum().toString() + String.format("%02d", user.getNum()),
                                user.getName()
                        )
                ).toList();
    }

    @Override
    public List<StayUserElement> getUserInfoByUserIds(List<UUID> userIds) {
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return userClient.queryUserInfoByUserId(userIds)
                .getUsers()
                .stream()
                .map(
                        user -> new StayUserElement(
                                user.getId(),
                                user.getGrade().toString() + user.getClassNum().toString() + String.format("%02d", user.getNum()),
                                user.getName()
                        )
                ).toList();
    }

    @Override
    public StayUserElement getUserInfo(UUID userId) {
        UserInfoResponseElement userInfo = userClient.queryUserInfo(userId);
        return new StayUserElement(
                userInfo.getId(),
                userInfo.getGrade().toString() + userInfo.getClassNum().toString() + String.format("%02d", userInfo.getNum()),
                userInfo.getName()
        );
    }
}
