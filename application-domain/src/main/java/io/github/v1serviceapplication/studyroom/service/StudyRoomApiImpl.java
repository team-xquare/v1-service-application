package io.github.v1serviceapplication.studyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.api.StudyRoomApi;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudyRoomElement;
import io.github.v1serviceapplication.studyroom.exception.FullStudyRoomException;
import io.github.v1serviceapplication.studyroom.spi.PostStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.QueryStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomQueryExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import io.github.v1serviceapplication.studyroom.spi.dto.StudyRoomModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class StudyRoomApiImpl implements StudyRoomApi {

    private final PostStudyRoomRepositorySpi postStudyRoomRepositorySpi;
    private final QueryStudyRoomRepositorySpi studyRoomRepositorySpi;
    private final StudyRoomQueryExtensionRepositorySpi studyRoomQueryExtensionRepositorySpi;
    private final StudyRoomUserFeignSpi studyRoomUserFeignSpi;
    private final UserIdFacade userIdFacade;

    @Override
    public void postStudyRoom(UUID studyRoomId) {
        Long applicationCount = postStudyRoomRepositorySpi.applicationCount(studyRoomId);
        StudyRoom studyRoom = postStudyRoomRepositorySpi.findById(studyRoomId);

        if (applicationCount >= studyRoom.getMaxPeopleCount()) {
            throw FullStudyRoomException.EXCEPTION;
        }

        saveOrUpdate(userIdFacade.getCurrentUserId(), studyRoomId);
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
        return studyRoomQueryExtensionRepositorySpi.findTodayByStudyRoomId(studyRoomId);
    }

    @Override
    public UUID queryStudyRoomStatus() {
        return studyRoomRepositorySpi.findStudyRoomIdByUserId(userIdFacade.getCurrentUserId())
                .map(StudyRoom::getId)
                .orElse(null);
    }

}
