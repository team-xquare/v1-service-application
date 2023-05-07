package io.github.v1serviceapplication.infrastructure.notification.scheduler;

import io.github.v1serviceapplication.notification.NotificationSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationSpi notificationSpi;
    private static final String APPLICATION_WEEKEND_PICNIC = "APPLICATION_WEEKEND_PICNIC";
    private static final String WEEKEND_APPLICATION_CONTENT = "주말외출 예약 기간입니다.";
    private static final String THREAD_ID = "weekend-application";


    @Scheduled(cron = "0 0 22 ? * FRI", zone = "Asia/Seoul")
    public void weekendApplicationNotification() {
        notificationSpi.sendGroupNotification(
                APPLICATION_WEEKEND_PICNIC,
                WEEKEND_APPLICATION_CONTENT,
                THREAD_ID);
    }
}
