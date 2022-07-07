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
    public UUID queryStudyRoomStatus(UUID userId) {
        return studyRoomRepositorySpi.findStudyRoomIdByUserId(userId)
                .map(StudyRoom::getId)
                .orElse(null);
    }
}
