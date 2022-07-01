package io.github.v1serviceapplication.studyroom.querystudyroom.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.QueryStudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.dto.response.StudyRoomElement;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.StudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class QueryStudyRoomImpl implements QueryStudyRoom {

    private final StudyRoomRepositorySpi studyRoomRepositorySpi;

    @Override
    public List<StudyRoomElement> queryStudyRooms(List<Integer> floorList) {
        List<StudyRoomModel> studyRoomModelList;

        if(floorList.isEmpty()) {
            studyRoomModelList = studyRoomRepositorySpi.findAll();
        } else {
            studyRoomModelList = studyRoomRepositorySpi.findAllByFloorIn(floorList);
        }

        return studyRoomModelList
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
                .students(Collections.emptyList())          //TODO user id list로 정보 불러오기.
                .build();
    }

}
