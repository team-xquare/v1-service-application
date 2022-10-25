package io.github.v1serviceapplication.studyroom.spi;

import java.util.List;
import java.util.UUID;

public interface StudyRoomQueryExtensionRepositorySpi {
    List<UUID> findStudentIdByRoomIdAndToday(UUID studyRoomId);
}
