package io.github.v1serviceapplication.picnic.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class PicnicApiImpl implements PicnicApi {
    private final PicnicRepositorySpi picnicRepositorySpi;
    private final UserIdFacade userIdFacade;

    @Override
    public void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request) {
        Picnic picnic = Picnic.builder()
                .userId(userIdFacade.getCurrentUserId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .arrangement(request.getArrangement())
                .build();

        picnicRepositorySpi.applyWeekendPicnic(picnic);
    }
}
