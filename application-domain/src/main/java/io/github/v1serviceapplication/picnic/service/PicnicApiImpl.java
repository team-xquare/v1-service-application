package io.github.v1serviceapplication.picnic.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.error.InvalidPicnicApplicationTimeException;
import io.github.v1serviceapplication.error.PicnicApplyNotAvailableException;
import io.github.v1serviceapplication.error.PicnicNotFoundException;
import io.github.v1serviceapplication.error.PicnicPassModifyForbiddenException;
import io.github.v1serviceapplication.error.UserNotEmptyException;
import io.github.v1serviceapplication.error.UserNotFoundException;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.PicnicAllowTimeResponse;
import io.github.v1serviceapplication.picnic.api.dto.PicnicDetail;
import io.github.v1serviceapplication.picnic.api.dto.PicnicElement;
import io.github.v1serviceapplication.picnic.api.dto.PicnicListResponse;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.api.dto.StudentPicnicDetail;
import io.github.v1serviceapplication.picnic.api.dto.UpdatePicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.WeekendPicnicExcelElement;
import io.github.v1serviceapplication.picnic.api.dto.WeekendPicnicExcelListResponse;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import io.github.v1serviceapplication.picnic.spi.PicnicUserFeignSpi;
import io.github.v1serviceapplication.picnicdatetime.PicnicTime;
import io.github.v1serviceapplication.picnicdatetime.TimeType;
import io.github.v1serviceapplication.picnicdatetime.spi.PicnicTimeRepositorySpi;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import io.github.v1serviceapplication.user.UserIdFacade;
import io.github.v1serviceapplication.user.dto.response.UserInfoElement;
import io.github.v1serviceapplication.user.spi.UserFeignSpi;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
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
    private final UserFeignSpi userFeignSpi;
    private final PicnicTimeRepositorySpi picnicTimeRepositorySpi;

    @Override
    public void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request) {
        UUID userId = userIdFacade.getCurrentUserId();
        List<LocalTime> picnicRequestTime = getPicnicRequestTimeList();
        List<Picnic> userPicnics = picnicRepositorySpi.findAllByUserIdAndDormitoryReturnCheckTime(userId, picnicRequestTime);
        if (!userPicnics.isEmpty()) {
            throw UserNotEmptyException.EXCEPTION;
        }
        validateRequestTime(request);

        Picnic picnic = Picnic.builder()
                .userId(userId)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .createDateTime(LocalDateTime.now())
                .reason(request.getReason())
                .arrangement(request.getArrangement())
                .build();

        picnicRepositorySpi.applyWeekendPicnic(picnic);
    }

    private void validateRequestTime(ApplyWeekendPicnicDomainRequest request) {
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw InvalidPicnicApplicationTimeException.EXCEPTION;
        }

        LocalDateTime nowDateTime = LocalDateTime.now();
        List<LocalTime> picnicRequestTime = getPicnicRequestTimeList();

        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), picnicRequestTime.get(0));
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), picnicRequestTime.get(1));

        switch (nowDateTime.toLocalDate().getDayOfWeek()) {
            case FRIDAY -> {
                boolean isBeforeStartDateTime = nowDateTime.isBefore(startDateTime);
                if (isBeforeStartDateTime) {
                    throw PicnicApplyNotAvailableException.EXCEPTION;
                }
            }
            case SATURDAY -> {
                boolean isBeforeStartDateTimeAndAfterEndDateTime = nowDateTime.isBefore(startDateTime) && nowDateTime.isAfter(endDateTime);
                if (isBeforeStartDateTimeAndAfterEndDateTime) {
                    throw PicnicApplyNotAvailableException.EXCEPTION;
                }
            }
            case SUNDAY -> {
                boolean isAfterEndDateTime = nowDateTime.isAfter(endDateTime);
                if (isAfterEndDateTime) {
                    throw PicnicApplyNotAvailableException.EXCEPTION;
                }
            }
            default -> {
                throw PicnicApplyNotAvailableException.EXCEPTION;
            }
        }
    }

    @Override
    public PicnicListResponse weekendPicnicList() {
        List<LocalTime> picnicRequestTime = getPicnicRequestTimeList();
        List<Picnic> picnics = picnicRepositorySpi.findAllByToday(picnicRequestTime);
        List<UUID> picnicUserIds = picnicRepositorySpi.findUserIdByToday(picnicRequestTime);
        if (picnicUserIds.isEmpty()) {
            return new PicnicListResponse(List.of());
        }
        Map<UUID, UserInfoElement> hashMap = userFeignSpi.getUserInfoList(picnicUserIds).stream()
                .collect(Collectors.toMap(UserInfoElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<PicnicElement> picnicElements = picnics.stream()
                .map(picnic -> {
                            UserInfoElement user = hashMap.get(picnic.getUserId());

                            if (user == null) {
                                throw UserNotFoundException.EXCEPTION;
                            }
                            return PicnicElement.builder()
                                    .id(picnic.getId())
                                    .userId(user.getUserId())
                                    .name(user.getName())
                                    .num(user.getNum())
                                    .startTime(picnic.getStartTime())
                                    .endTime(picnic.getEndTime())
                                    .build();
                        }
                ).sorted(Comparator.comparing(PicnicElement::getNum)).toList();

        return new PicnicListResponse(picnicElements);
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
        List<LocalTime> picnicRequestTime = getPicnicRequestTimeList();
        List<Picnic> weekendPicnicList = picnicRepositorySpi.findAllByToday(picnicRequestTime);

        Map<UUID, StudentElement> hashMap = picnicUserFeignSpi.queryAllUser().stream()
                .collect(Collectors.toMap(StudentElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

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
                                    .build();

                        }
                ).sorted(Comparator.comparing(WeekendPicnicExcelElement::getNum)).toList();

        return new WeekendPicnicExcelListResponse(weekendPicnicExcelElements);
    }

    @Override
    public StudentPicnicDetail getStudentPicnicDetail() {
        UUID userId = userIdFacade.getCurrentUserId();
        List<LocalTime> picnicRequestTime = getPicnicRequestTimeList();
        Picnic picnic = picnicRepositorySpi.findByUserIdAndCreateDateTimeByPresentPicnic(userId, picnicRequestTime);

        return StudentPicnicDetail.builder()
                .startTime(picnic.getStartTime())
                .endTime(picnic.getEndTime())
                .arrangement(picnic.getArrangement())
                .reason(picnic.getReason())
                .createDateTime(picnic.getCreateDateTime())
                .build();
    }

    @Override
    public void updateWeekendPicnic(UpdatePicnicDomainRequest request) {
        UUID userId = userIdFacade.getCurrentUserId();
        List<LocalTime> picnicRequestTime = getPicnicRequestTimeList();

        Picnic picnic = picnicRepositorySpi.findByUserIdAndCreateDateTimeByPresentPicnic(userId, picnicRequestTime);

        if (!userId.equals(picnic.getUserId())) {
            throw PicnicPassModifyForbiddenException.EXCEPTION;
        }

        picnicRepositorySpi.updateWeekendPicnic(picnic.getId(), request);
    }

    @Override
    public void deleteWeekendPicnic() {
        picnicRepositorySpi.deletePicnic(userIdFacade.getCurrentUserId());
    }

    @Override
    public PicnicAllowTimeResponse getPicnicAllowTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DayOfWeek currentDayOfWeek = currentDateTime.getDayOfWeek();
        List<PicnicTime> picnicAllowTime;

        boolean isSaturdayAndAfterPicnicRequestStartTime = currentDayOfWeek == DayOfWeek.SATURDAY &&
                currentDateTime.toLocalTime().isAfter(LocalTime.of(20, 29));
        boolean isSundayAndBeforePicnicRequestEndTime = currentDayOfWeek == DayOfWeek.SUNDAY &&
                currentDateTime.toLocalTime().isBefore(LocalTime.of(17, 1));

        if (isSaturdayAndAfterPicnicRequestStartTime || isSundayAndBeforePicnicRequestEndTime) {
            picnicAllowTime = picnicTimeRepositorySpi.getPicnicTime(List.of(TimeType.PICNIC_ALLOW_START_TIME_SUN, TimeType.PICNIC_ALLOW_END_TIME_SUN));
        } else {
            picnicAllowTime = picnicTimeRepositorySpi.getPicnicTime(List.of(TimeType.PICNIC_ALLOW_START_TIME, TimeType.PICNIC_ALLOW_END_TIME));
        }

        return new PicnicAllowTimeResponse(picnicAllowTime.get(0).getPicnicTime(), picnicAllowTime.get(1).getPicnicTime(), picnicAllowTime.get(0).getDay());
    }

    private List<LocalTime> getPicnicRequestTimeList() {
        return picnicTimeRepositorySpi.getPicnicAllowTime(List.of(TimeType.PICNIC_REQUEST_START_TIME, TimeType.PICNIC_REQUEST_END_TIME));
    }
}
