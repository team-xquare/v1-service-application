package io.github.v1serviceapplication.picnic.api;

import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.PicnicListResponse;

import java.util.UUID;

public interface PicnicApi {
    void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request);


    PicnicListResponse applyWeekendPicnics();

    void updateDormitoryReturnTime(UUID picnicId);
}
