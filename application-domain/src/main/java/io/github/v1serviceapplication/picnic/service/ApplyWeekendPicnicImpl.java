package io.github.v1serviceapplication.picnic.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.ApplyWeekendPicnic;
import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ApplyWeekendPicnicImpl implements ApplyWeekendPicnic {
    private final PicnicRepositorySpi picnicRepositorySpi;

    @Override
    public void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request) {
        UUID userId = UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398");  // todo request header에서 userId 가져오기

        Picnic picnic = Picnic.builder()
                .userId(userId)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .arrangement(request.getArrangement())
                .build();

        picnicRepositorySpi.applyWeekendPicnic(picnic);
    }
}
