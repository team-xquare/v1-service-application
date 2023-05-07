package io.github.v1serviceapplication.notification;

import io.github.v1serviceapplication.annotation.Spi;

import java.util.UUID;

@Spi
public interface NotificationSpi {
    void sendNotification(UUID userId, String topic, String content, String threadId);
    void sendGroupNotification(String topic, String content, String threadId);
}
