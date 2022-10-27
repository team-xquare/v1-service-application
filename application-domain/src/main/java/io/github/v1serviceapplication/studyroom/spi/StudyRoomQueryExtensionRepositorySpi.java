package io.github.v1serviceapplication.studyroom.spi;

import io.github.v1serviceapplication.studyroom.extension.Extension;

import java.util.List;
import java.util.UUID;

public interface StudyRoomQueryExtensionRepositorySpi {
    List<UUID> findStudentIdByRoomIdAndToday(UUID studyRoomId);
    Extension todayStudyRoomApply(UUID userId);
}
