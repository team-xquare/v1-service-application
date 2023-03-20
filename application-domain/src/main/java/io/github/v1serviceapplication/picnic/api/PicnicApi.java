package io.github.v1serviceapplication.picnic.api;

import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.PicnicDetail;
import io.github.v1serviceapplication.picnic.api.dto.PicnicListResponse;

import java.util.UUID;

public interface PicnicApi {
    void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request);

    PicnicListResponse weekendPicnicList(String type);

    void updateDormitoryReturnTime(UUID picnicId);

    void acceptPicnic(UUID picnicId);

    void refusePicnic(UUID picnicId);
    PicnicDetail getPicnicDetail(UUID picnicId);
}
