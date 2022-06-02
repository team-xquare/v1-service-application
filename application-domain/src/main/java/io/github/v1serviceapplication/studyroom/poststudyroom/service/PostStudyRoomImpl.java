package io.github.v1serviceapplication.studyroom.poststudyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.poststudyroom.api.PostStudyRoom;
import io.github.v1serviceapplication.studyroom.poststudyroom.spi.PostStudyRoomRepositorySpi;

import java.util.UUID;

@DomainService
public class PostStudyRoomImpl implements PostStudyRoom {

    public PostStudyRoomImpl(PostStudyRoomRepositorySpi postStudyRoomRepositorySpi) {
        this.postStudyRoomRepositorySpi = postStudyRoomRepositorySpi;
    }

    private final PostStudyRoomRepositorySpi postStudyRoomRepositorySpi;

    @Override
    public void postStudyRoom(UUID studyRoomId) {
        UUID userId = UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398");            //TODO userId 가져오기.

        Long totalCount = postStudyRoomRepositorySpi.totalCount(userId);

        if(totalCount >= 1) {
            throw new RuntimeException(); //TODO Exception
        }

        Long applicationCount = postStudyRoomRepositorySpi.applicationCount(studyRoomId);
        StudyRoom studyRoom = postStudyRoomRepositorySpi.findById(studyRoomId);

        if(applicationCount >= studyRoom.getMaxPeopleCount()) {
            throw new RuntimeException(); //TODO Exception
        }

        postStudyRoomRepositorySpi.postStudyRoom(studyRoomId, userId);
    }

}
