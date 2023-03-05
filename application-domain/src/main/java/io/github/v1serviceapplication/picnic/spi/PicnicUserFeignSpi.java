package io.github.v1serviceapplication.picnic.spi;

import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;

import java.util.List;
import java.util.UUID;

public interface PicnicUserFeignSpi {

    List<PicnicUserElement> getUserInfoByUserId(List<UUID> userId);
}
