package io.github.v1serviceapplication.notification;

import io.github.v1serviceapplication.annotation.Spi;

import java.util.List;
import java.util.UUID;

@Spi
public interface NotificationSpi {
    void sendNotification(UUID userId, String category, String content, String threadId);
    void sendGroupNotification(String category, String content, String threadId);
    void sendSpecificGroupNotification(List<UUID> userIdList, String category, String content, String threadId);
}
