package io.github.v1serviceapplication.infrastructure.notification.scheduler;

import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.IsHomecomingResponse;
import io.github.v1serviceapplication.infrastructure.feign.client.schedule.ScheduleClient;
import io.github.v1serviceapplication.notification.NotificationSpi;
import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    private final StayApi stayApi;
    private final ScheduleClient scheduleClient;

    @Scheduled(cron = "0 0 21 * * FRI-SAT", zone = "Asia/Seoul")
    public void weekendApplicationNotification() {
        IsHomecomingResponse isHomecomingDay = scheduleClient.queryIsHomecomingDay(LocalDate.now());
        List<UUID> userIdList = stayApi.queryUserIdListByStatus(StayStatusCode.STAY);
        if (!isHomecomingDay.isHomecomingDay()) {
            sendSpecificGroupNotification(userIdList, APPLICATION_WEEKEND_PICNIC, WEEKEND_PICNIC_CONTENT, THREAD_ID);
        }
    }

    @Scheduled(cron = "0 20 8 * * *", zone = "Asia/Seoul")
    public void weekendMealNotification() {
        boolean isAllowedWeekendMealPeriod = weekendMealApi.queryWeekendMealIsAllowedPeriod().isAllowedPeriod();
        List<UUID> userIdList = weekendMealApi.queryUserIdListByStatus(WeekendMealApplicationStatus.NON_RESPONSE);

        if (isAllowedWeekendMealPeriod){
            sendSpecificGroupNotification(userIdList, APPLICATION_WEEKEND_MEAL, WEEKEND_MEAL_CONTENT, THREAD_ID);
        }
    }

    @Scheduled(cron = "0 3 16 * * *", zone = "Asia/Seoul")
    public void test() {
        List<UUID> userIdList = weekendMealApi.queryUserIdByStatus(WeekendMealApplicationStatus.NON_RESPONSE);
        notificationSpi.sendSpecificGroupNotification(userIdList, APPLICATION_WEEKEND_MEAL, WEEKEND_MEAL_CONTENT, THREAD_ID);
    }

    private void sendSpecificGroupNotification(List<UUID> userIdList, String topic, String content, String threadId) {
        notificationSpi.sendSpecificGroupNotification(userIdList, topic, content, threadId);
    }
}
