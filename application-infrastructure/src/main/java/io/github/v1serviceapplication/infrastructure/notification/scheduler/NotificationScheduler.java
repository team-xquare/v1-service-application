package io.github.v1serviceapplication.infrastructure.notification.scheduler;

import io.github.v1serviceapplication.notification.NotificationSpi;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealAllowedPeriodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationScheduler {

    private static final String APPLICATION_WEEKEND_PICNIC = "APPLICATION_WEEKEND_PICNIC";
    private static final String WEEKEND_PICNIC_CONTENT = "주말외출 예약 기간입니다.";
    private static final String THREAD_ID = "weekend-application";
    private static final String APPLICATION_WEEKEND_MEAL = "APPLICATION_WEEKEND_MEAL";
    private static final String WEEKEND_MEAL_CONTENT = "주말 급식 신청 기간입니다.";

    private final NotificationSpi notificationSpi;
    private final WeekendMealApi weekendMealApi;

    @Scheduled(cron = "0 0 21 * * FRI-SAT", zone = "Asia/Seoul")
    public void weekendApplicationNotification() {
        sendGroupNotification(APPLICATION_WEEKEND_PICNIC, WEEKEND_PICNIC_CONTENT, THREAD_ID);
    }

    @Scheduled(cron = "0 20 8 * * *", zone = "Asia/Seoul")
    public void weekendMealNotification() {
        boolean isAllowedWeekendMealPeriod = weekendMealApi.queryWeekendMealIsAllowedPeriod().isAllowedPeriod();
        if (isAllowedWeekendMealPeriod){
            sendGroupNotification(APPLICATION_WEEKEND_MEAL, WEEKEND_MEAL_CONTENT, THREAD_ID);
        }
    }

    private void sendGroupNotification(String topic, String content, String threadId) {
        notificationSpi.sendGroupNotification(topic, content, threadId);
    }
}
