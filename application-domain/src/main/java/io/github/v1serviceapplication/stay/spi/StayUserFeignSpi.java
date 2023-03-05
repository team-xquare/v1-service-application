package io.github.v1serviceapplication.stay.spi;

import io.github.v1serviceapplication.stay.api.dto.response.StayUserElement;

import java.util.List;
import java.util.UUID;

public interface StayUserFeignSpi {
    List<StayUserElement> getUserInfoByUserIds(List<UUID> userIds);

    StayUserElement getUserInfo(UUID userId);
}
