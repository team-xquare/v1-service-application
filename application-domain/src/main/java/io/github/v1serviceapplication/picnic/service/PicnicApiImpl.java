package io.github.v1serviceapplication.picnic.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.error.PicnicNotFoundException;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.PicnicElement;
import io.github.v1serviceapplication.picnic.api.dto.PicnicListResponse;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import io.github.v1serviceapplication.picnic.spi.PicnicUserFeignSpi;
import lombok.RequiredArgsConstructor;

import java.nio.channels.NotYetBoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
public class PicnicApiImpl implements PicnicApi {
    private final PicnicRepositorySpi picnicRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final PicnicUserFeignSpi picnicUserFeignSpi;

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


    @Override
    public PicnicListResponse applyWeekendPicnics() {
        List<Picnic> picnics = picnicRepositorySpi.findAllByToday();
        List<UUID> picnicUserIds = picnicRepositorySpi.findUserIdByToday();
        if (picnicUserIds.isEmpty()) {
            return new PicnicListResponse(List.of());
        }
        Map<UUID, PicnicUserElement> hashMap = picnicUserFeignSpi.getUserInfoByUserId(picnicUserIds).stream()
                .collect(Collectors.toMap(PicnicUserElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<PicnicElement> picnicElements = picnics.stream()
                .map(picnic -> {
                            PicnicUserElement user = hashMap.get(picnic.getUserId());
                            return PicnicElement.builder()
                                    .userId(user.getUserId())
                                    .name(user.getName())
                                    .num(user.getNum())
                                    .startTime(picnic.getStartTime())
                                    .endTime(picnic.getEndTime())
                                    .isAcceptance(picnic.getIsAcceptance())
                                    .build();
                        }
                ).filter(picnicElement -> !picnicElement.getIsAcceptance()).toList();

        return new PicnicListResponse(picnicElements);
    }

    @Override
    public void updateDormitoryReturnTime(UUID picnicId) {
        picnicRepositorySpi.findByPicnicId(picnicId).orElseThrow(() -> PicnicNotFoundException.EXCEPTION);
        picnicRepositorySpi.updateDormitoryReturnTime(picnicId);
    }

    @Override
    public void acceptPicnic(UUID picnicId) {
        picnicRepositorySpi.findByPicnicId(picnicId).orElseThrow(() -> PicnicNotFoundException.EXCEPTION);
        picnicRepositorySpi.acceptPicnic(picnicId);
    }

    @Override
    public void refusePicnic(UUID picnicId) {
        picnicRepositorySpi.findByPicnicId(picnicId).orElseThrow(() -> PicnicNotFoundException.EXCEPTION);
        picnicRepositorySpi.refusePicnic(picnicId);
    }
}
