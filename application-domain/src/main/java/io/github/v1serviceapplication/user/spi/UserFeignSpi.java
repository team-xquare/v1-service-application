package io.github.v1serviceapplication.user.spi;

import io.github.v1serviceapplication.user.dto.response.UserInfoElement;

import java.util.List;
import java.util.UUID;

public interface UserFeignSpi {
    List<UserInfoElement> getUserInfoList(List<UUID> userIds);
}
