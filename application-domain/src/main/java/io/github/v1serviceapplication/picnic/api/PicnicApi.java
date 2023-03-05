package io.github.v1serviceapplication.picnic.api;

import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.PicnicListResponse;

public interface PicnicApi {
    void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request);

    PicnicListResponse applyWeekendPicnics();
}
