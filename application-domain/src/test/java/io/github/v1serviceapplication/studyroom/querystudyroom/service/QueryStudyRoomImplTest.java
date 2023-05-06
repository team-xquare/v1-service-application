package io.github.v1serviceapplication.studyroom.querystudyroom.service;

import io.github.v1serviceapplication.stubs.InMemoryQueryStudyRoomRepository;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import io.github.v1serviceapplication.studyroom.extension.Extension;
import io.github.v1serviceapplication.studyroom.spi.PostStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomPostExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomQueryExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;

import java.util.List;
import java.util.UUID;

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
            return List.of(new StudentElement(UUID.randomUUID(), "Test", 0, 0, 0, "Test Image path"));
        }

        @Override
        public List<StudentElement> queryAllUser() {
            return queryAllUser();
        }
    };



}