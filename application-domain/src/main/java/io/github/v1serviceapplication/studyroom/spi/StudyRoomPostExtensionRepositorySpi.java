package io.github.v1serviceapplication.studyroom.spi;

import java.util.UUID;

public interface StudyRoomPostExtensionRepositorySpi {
    void deleteById(UUID extensionId);
}
