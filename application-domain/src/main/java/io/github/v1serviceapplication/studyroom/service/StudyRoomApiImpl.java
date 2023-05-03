package io.github.v1serviceapplication.studyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.user.UserIdFacade;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.api.StudyRoomApi;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudyRoomElement;
import io.github.v1serviceapplication.studyroom.exception.ExtensionNotFoundException;
import io.github.v1serviceapplication.studyroom.exception.FullStudyRoomException;
import io.github.v1serviceapplication.studyroom.exception.InCorrectStudyRoomIdException;
import io.github.v1serviceapplication.studyroom.exception.InvalidStudyRoomApplicationTimeException;
import io.github.v1serviceapplication.studyroom.extension.Extension;
import io.github.v1serviceapplication.studyroom.spi.PostStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.QueryStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomPostExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomQueryExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import io.github.v1serviceapplication.studyroom.spi.dto.StudyRoomModel;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class StudyRoomApiImpl implements StudyRoomApi {

    private final PostStudyRoomRepositorySpi postStudyRoomRepositorySpi;
    private final QueryStudyRoomRepositorySpi studyRoomRepositorySpi;
    private final StudyRoomQueryExtensionRepositorySpi studyRoomQueryExtensionRepositorySpi;
    private final StudyRoomPostExtensionRepositorySpi studyRoomPostExtensionRepositorySpi;
    private final StudyRoomUserFeignSpi studyRoomUserFeignSpi;
    private final UserIdFacade userIdFacade;

    @Override
    public void postStudyRoom(UUID studyRoomId) {
        Long applicationCount = postStudyRoomRepositorySpi.applicationCount(studyRoomId);
        StudyRoom studyRoom = postStudyRoomRepositorySpi.findById(studyRoomId);

        checkIsBeforeSchoolEndTime();

        checkIsFullStudyRoom(applicationCount, studyRoom);

        saveOrUpdate(userIdFacade.getCurrentUserId(), studyRoomId);
    }

    private void checkIsBeforeSchoolEndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime schoolEndTime = LocalTime.of(20, 30);

        boolean isWeekdays = currentDateTime.toLocalDate().getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
        boolean isBeforeSchoolEndTime = currentDateTime.toLocalTime().isBefore(schoolEndTime);
        if (isWeekdays && isBeforeSchoolEndTime) {
            throw InvalidStudyRoomApplicationTimeException.EXCEPTION;
        }
    }

    private void checkIsFullStudyRoom(Long applicationCount, StudyRoom studyRoom) {
        boolean isFullStudyRoom = applicationCount >= studyRoom.getMaxPeopleCount();
        if (isFullStudyRoom) {
            throw FullStudyRoomException.EXCEPTION;
        }
    }

    @Override
    public void cancelExtension(UUID studyRoomId) {
        Extension extension = studyRoomQueryExtensionRepositorySpi.todayStudyRoomApply(userIdFacade.getCurrentUserId());

        if (extension == null) {
            throw ExtensionNotFoundException.EXCEPTION;
        }

        if (!extension.getStudyRoomId().equals(studyRoomId)) {
            throw InCorrectStudyRoomIdException.EXCEPTION;
        }

        studyRoomPostExtensionRepositorySpi.deleteById(extension.getId());
    }

    private void saveOrUpdate(UUID userId, UUID studyRoomId) {
        if (postStudyRoomRepositorySpi.todayStudyRoomApplyExist(userId)) {
            postStudyRoomRepositorySpi.updateStudyRoom(studyRoomId, userId);
        } else {
            postStudyRoomRepositorySpi.postStudyRoom(studyRoomId, userId);
        }
    }

    @Override
    public List<StudyRoomElement> queryStudyRooms() {
        return studyRoomRepositorySpi.findAll()
                .stream()
                .map(this::buildStudyRoom)
                .collect(Collectors.toList());
    }

    private StudyRoomElement buildStudyRoom(StudyRoomModel studyRoom) {
        return StudyRoomElement.builder()
                .id(studyRoom.getId())
                .studyRoomName(studyRoom.getName())
                .applicationCount(studyRoom.getApplicationCount())
                .maxPeopleCount(studyRoom.getMaxPeopleCount())
                .students(studyRoomUserFeignSpi.queryUserInfoByUserId(queryStudentIdList(studyRoom.getId())))
                .build();
    }

    private List<UUID> queryStudentIdList(UUID studyRoomId) {
        return studyRoomQueryExtensionRepositorySpi.findStudentIdByRoomIdAndToday(studyRoomId);
    }

    @Override
    public UUID queryStudyRoomStatus() {
        return studyRoomRepositorySpi.findStudyRoomIdByUserId(userIdFacade.getCurrentUserId())
                .map(StudyRoom::getId)
                .orElse(null);
    }

}
