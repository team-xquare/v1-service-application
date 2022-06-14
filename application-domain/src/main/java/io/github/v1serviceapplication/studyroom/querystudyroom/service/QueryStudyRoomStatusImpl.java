package io.github.v1serviceapplication.studyroom.querystudyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.QueryStudyRoomStatus;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.StudyRoomRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class QueryStudyRoomStatusImpl implements QueryStudyRoomStatus {

    private final StudyRoomRepositorySpi studyRoomRepositorySpi;

    @Override
    public UUID queryStudyRoomStatus() {
        return studyRoomRepositorySpi.findStudyRoomIdByUserId(
                        UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398") // TODO userId
                )
                .map(StudyRoom::getId)
                .orElse(null);
    }
}
