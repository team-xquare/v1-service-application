package io.github.v1serviceapplication.picnic.spi;

import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;

import java.util.List;
import java.util.UUID;

public interface PicnicUserFeignSpi {

    List<PicnicUserElement> getUserInfoByUserId(List<UUID> userId);

    List<StudentElement> queryAllUser();
}
