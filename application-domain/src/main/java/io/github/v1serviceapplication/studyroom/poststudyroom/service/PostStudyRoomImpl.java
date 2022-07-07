package io.github.v1serviceapplication.studyroom.poststudyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.poststudyroom.api.PostStudyRoom;
import io.github.v1serviceapplication.studyroom.poststudyroom.exception.FullStudyRoomException;
import io.github.v1serviceapplication.studyroom.poststudyroom.spi.PostStudyRoomRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class PostStudyRoomImpl implements PostStudyRoom {

    private final PostStudyRoomRepositorySpi postStudyRoomRepositorySpi;

    @Override
    public void postStudyRoom(UUID studyRoomId, UUID userId) {
        Long applicationCount = postStudyRoomRepositorySpi.applicationCount(studyRoomId);
        StudyRoom studyRoom = postStudyRoomRepositorySpi.findById(studyRoomId);

        if (applicationCount >= studyRoom.getMaxPeopleCount()) {
            throw FullStudyRoomException.EXCEPTION;
        }

        saveOrUpdate(userId, studyRoomId);
    }

    private void saveOrUpdate(UUID userId, UUID studyRoomId) {
        if (postStudyRoomRepositorySpi.todayStudyRoomApplyExist(userId)) {
            postStudyRoomRepositorySpi.updateStudyRoom(studyRoomId, userId);
        } else {
            postStudyRoomRepositorySpi.postStudyRoom(studyRoomId, userId);
        }
    }

}
