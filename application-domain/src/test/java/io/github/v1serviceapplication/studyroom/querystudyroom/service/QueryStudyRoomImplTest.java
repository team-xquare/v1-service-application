package io.github.v1serviceapplication.studyroom.querystudyroom.service;

import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.studyroom.api.StudyRoomApi;
import io.github.v1serviceapplication.studyroom.extension.Extension;
import io.github.v1serviceapplication.stubs.InMemoryQueryStudyRoomRepository;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.service.StudyRoomApiImpl;
import io.github.v1serviceapplication.studyroom.spi.PostStudyRoomRepositorySpi;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class QueryStudyRoomImplTest {

    private final PostStudyRoomRepositorySpi postStudyRoomRepositorySpi = mock(PostStudyRoomRepositorySpi.class);
    private final InMemoryQueryStudyRoomRepository studyRoomRepositorySpi = new InMemoryQueryStudyRoomRepository();
    private final UserIdFacade userIdFacade = new UserIdFacade() {
        @Override
        public UUID getCurrentUserId() {
            return null;
        }
    };
    private final StudyRoomApi studyRoomApi = new StudyRoomApiImpl(postStudyRoomRepositorySpi, studyRoomRepositorySpi, userIdFacade);

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

        studyRoomRepositorySpi.saveStudyRoom(studyRoom);
        studyRoomRepositorySpi.saveExtension(extension);

        studyRoomApi.queryStudyRooms()
                .forEach(queryStudy -> {
                    assertThat(queryStudy.getId()).isEqualTo(studyRoomId);
                    assertThat(queryStudy.getStudyRoomName()).isEqualTo(studyRoomName);
                    assertThat(queryStudy.getApplicationCount()).isEqualTo(1);
                    queryStudy.getStudents().forEach(id -> assertThat(id).isEqualTo(userId));
                });

    }


}