package io.github.v1serviceapplication.picnic.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.error.InvalidPicnicApplicationTimeException;
import io.github.v1serviceapplication.error.PicnicApplyNotAvailableException;
import io.github.v1serviceapplication.error.PicnicNotFoundException;
import io.github.v1serviceapplication.error.UserNotEmptyException;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.*;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import io.github.v1serviceapplication.picnic.spi.PicnicUserFeignSpi;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DomainService
@RequiredArgsConstructor
public class PicnicApiImpl implements PicnicApi {
    private final PicnicRepositorySpi picnicRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final PicnicUserFeignSpi picnicUserFeignSpi;

    @Override
    public void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request) {
        UUID userId = userIdFacade.getCurrentUserId();
        List<Picnic> userPicnics = picnicRepositorySpi.findAllByUserIdAndIsAcceptance(userId);
        if (!userPicnics.isEmpty()) {
            throw UserNotEmptyException.EXCEPTION;
        }
        validateRequestTime(request);

        Picnic picnic = Picnic.builder()
                .userId(userId)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .arrangement(request.getArrangement())
                .isAcceptance(false)
                .build();

        picnicRepositorySpi.applyWeekendPicnic(picnic);
    }

    private void validateRequestTime(ApplyWeekendPicnicDomainRequest request) {
        LocalTime nowTime = LocalTime.now();
        LocalTime eneTime = LocalTime.of(23, 00);

        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw InvalidPicnicApplicationTimeException.EXCEPTION;
        }
        if (nowTime.isAfter(eneTime)) {
            throw PicnicApplyNotAvailableException.EXCEPTION;
        }
    }


    @Override
    public PicnicListResponse weekendPicnicList(String type) {
        List<Picnic> picnics = picnicRepositorySpi.findAllByToday();
        List<UUID> picnicUserIds = picnicRepositorySpi.findUserIdByToday();
        if (picnicUserIds.isEmpty()) {
            return new PicnicListResponse(List.of());
        }
        Map<UUID, PicnicUserElement> hashMap = picnicUserFeignSpi.getUserInfoByUserId(picnicUserIds).stream()
                .collect(Collectors.toMap(PicnicUserElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<PicnicElement> picnicElements = picnics.stream()
                .filter(
                        picnic -> {
                            if (type.equals("AWAIT")) {
                                return !picnic.getIsAcceptance();
                            } else if (type.equals("RETURN")) {
                                return picnic.getIsAcceptance() && picnic.getDormitoryReturnCheckTime() == null;
                            } else {
                                return true;
                            }
                        }
                )
                .map(picnic -> {
                            PicnicUserElement user = hashMap.get(picnic.getUserId());
                            return PicnicElement.builder()
                                    .id(picnic.getId())
                                    .userId(user.getUserId())
                                    .name(user.getName())
                                    .num(user.getNum())
                                    .startTime(picnic.getStartTime())
                                    .endTime(picnic.getEndTime())
                                    .isAcceptance(picnic.getIsAcceptance())
                                    .build();
                        }
                ).toList();

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

    @Override
    public PicnicDetail getPicnicDetail(UUID picnicId) {
        Picnic picnics = picnicRepositorySpi.findByPicnicId(picnicId)
                .orElseThrow(() -> PicnicNotFoundException.EXCEPTION);
        PicnicUserElement user = picnicRepositorySpi.getUserInfo(picnics.getUserId());

        return PicnicDetail.builder()
                .num(user.getNum())
                .name(user.getName())
                .startTime(picnics.getStartTime())
                .endTime(picnics.getEndTime())
                .reason(picnics.getReason())
                .arrangement(picnics.getArrangement())
                .build();
    }

    @Override
    public WeekendPicnicExcelListResponse weekendPicnicExcel() {
        List<Picnic> weekendPicnicList = picnicRepositorySpi.findAllByToday();

        List<StudentElement> userList = picnicUserFeignSpi.queryAllUser();
        Map<UUID, StudentElement> hashMap = new HashMap<>();

        for (StudentElement student : userList) {
            hashMap.put(student.getUserId(), student);
        }

        List<WeekendPicnicExcelElement> weekendPicnicExcelElements = weekendPicnicList.stream()
                .map(picnic -> {
                    StudentElement user = hashMap.get(picnic.getUserId());

                    return WeekendPicnicExcelElement.builder()
                            .userId(user.getUserId())
                            .name(user.getStudentName())
                            .num(String.valueOf(user.getGrade()) + user.getClassNum() + String.format("%02d", user.getNum()))
                            .startTime(picnic.getStartTime())
                            .endTime(picnic.getEndTime())
                            .reason(picnic.getReason())
                            .arrangement(picnic.getArrangement())
                            .isAcceptance(picnic.getIsAcceptance())
                            .build();

                }).toList();

        return new WeekendPicnicExcelListResponse(weekendPicnicExcelElements);
    }

}

