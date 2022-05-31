package io.github.v1serviceapplication.studyroom.querystudyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.QueryStudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response.StudyRoomElement;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.StudyRoomRepositorySpi;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@DomainService
public class QueryStudyRoomImpl implements QueryStudyRoom {

    public QueryStudyRoomImpl(StudyRoomRepositorySpi studyRoomRepositorySpi) {
        this.studyRoomRepositorySpi = studyRoomRepositorySpi;
    }

    private final StudyRoomRepositorySpi studyRoomRepositorySpi;

    @Override
    public List<StudyRoomElement> queryStudyRooms() {
        return studyRoomRepositorySpi.findAll()
                .stream()
                .map(studyRoom ->
                        new StudyRoomElement(
                                studyRoom.getId(),
                                studyRoom.getName(),
                                studyRoom.getApplicationCount(),
                                Collections.emptyList()                 //TODO user id list로 정보 불러오기.
                        )
                ).collect(Collectors.toList());
    }

}
