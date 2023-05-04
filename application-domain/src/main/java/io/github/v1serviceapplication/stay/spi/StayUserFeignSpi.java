package io.github.v1serviceapplication.stay.spi;

import io.github.v1serviceapplication.user.dto.response.UserInfoElement;
import io.github.v1serviceapplication.stay.api.dto.response.StayUserElement;

import java.util.List;
import java.util.UUID;

public interface StayUserFeignSpi {

    StayUserElement getUserInfo(UUID userId);

    List<StayUserElement> getStudent();
}
