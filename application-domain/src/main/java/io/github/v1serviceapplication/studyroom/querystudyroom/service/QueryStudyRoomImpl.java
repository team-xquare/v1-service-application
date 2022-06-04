package io.github.v1serviceapplication.studyroom.querystudyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.QueryStudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response.StudyRoomElement;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.StudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;

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
                .map(this::buildStudyRoom)
                .collect(Collectors.toList());
    }

    private StudyRoomElement buildStudyRoom(StudyRoomModel studyRoom) {
        return StudyRoomElement.builder()
                .id(studyRoom.getId())
                .studyRoomName(studyRoom.getName())
                .applicationCount(studyRoom.getApplicationCount())
                .students(Collections.emptyList())          //TODO user id list로 정보 불러오기.
                .build();
    }

}
