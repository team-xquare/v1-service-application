package io.github.v1serviceapplication.studyroom.querystudyroom.service;

import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.studyroom.api.StudyRoomApi;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import io.github.v1serviceapplication.studyroom.extension.Extension;
import io.github.v1serviceapplication.stubs.InMemoryQueryStudyRoomRepository;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.service.StudyRoomApiImpl;
import io.github.v1serviceapplication.studyroom.spi.PostStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomPostExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomQueryExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class QueryStudyRoomImplTest {

    private final PostStudyRoomRepositorySpi postStudyRoomRepositorySpi = mock(PostStudyRoomRepositorySpi.class);
    private final InMemoryQueryStudyRoomRepository studyRoomRepositorySpi = new InMemoryQueryStudyRoomRepository();
    private final StudyRoomQueryExtensionRepositorySpi studyRoomQueryExtensionRepositorySpi = new StudyRoomQueryExtensionRepositorySpi() {
        @Override
        public List<UUID> findStudentIdByRoomIdAndToday(UUID studyRoomId) {
            return List.of(UUID.randomUUID());
        }

        @Override
        public Extension todayStudyRoomApply(UUID userId) {
            return Extension.builder().build();
        }
    };

    private final StudyRoomPostExtensionRepositorySpi studyRoomPostExtensionRepositorySpi = new StudyRoomPostExtensionRepositorySpi() {
        @Override
        public void deleteById(UUID extensionId) {

        }
    };

    private final StudyRoomUserFeignSpi studyRoomUserFeignSpi = new StudyRoomUserFeignSpi() {
        @Override
        public List<StudentElement> queryUserInfoByUserId(List<UUID> userId) {
            return List.of(new StudentElement("Test", "Test Image path"));
        }
    };
    private final UserIdFacade userIdFacade = new UserIdFacade() {
        @Override
        public UUID getCurrentUserId() {
            return null;
        }
    };
    private final StudyRoomApi studyRoomApi = new StudyRoomApiImpl(postStudyRoomRepositorySpi, studyRoomRepositorySpi, studyRoomQueryExtensionRepositorySpi, studyRoomPostExtensionRepositorySpi, studyRoomUserFeignSpi, userIdFacade);

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
                    queryStudy.getStudents().forEach(it -> {
                        assertThat(it.getStudentName()).isEqualTo("Test");
                        assertThat(it.getProfileImage()).isEqualTo("Test Image path");
                    });
                });

    }


}