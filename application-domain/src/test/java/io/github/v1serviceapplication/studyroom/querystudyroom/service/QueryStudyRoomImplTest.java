package io.github.v1serviceapplication.studyroom.querystudyroom.service;


import io.github.v1serviceapplication.studyroom.extension.Extension;
import io.github.v1serviceapplication.stubs.InMemoryStudyRoomRepository;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.QueryStudyRoom;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QueryStudyRoomImplTest {

    private final InMemoryStudyRoomRepository studyRoomRepository = new InMemoryStudyRoomRepository();
    private final QueryStudyRoom queryStudyRoom = new QueryStudyRoomImpl(studyRoomRepository);

    @Test
    void queryStudyRooms() {
        UUID studyRoomId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String studyRoomName = "가온실";

        StudyRoom studyRoom = StudyRoom.builder()
                .id(studyRoomId)
                .name(studyRoomName)
                .build();

        Extension extension = Extension.builder()
                .userId(userId)
                .studyRoomId(studyRoomId)
                .build();

        studyRoomRepository.saveStudyRoom(studyRoom);
        studyRoomRepository.saveExtension(extension);

        queryStudyRoom.queryStudyRooms()
                .forEach(queryStudy -> {
                    assertThat(queryStudy.getId()).isEqualTo(studyRoomId);
                    assertThat(queryStudy.getStudyRoomName()).isEqualTo(studyRoomName);
                    assertThat(queryStudy.getApplicationCount()).isEqualTo(1);
                    queryStudy.getStudents().forEach(id -> assertThat(id).isEqualTo(userId));
                });

    }


}