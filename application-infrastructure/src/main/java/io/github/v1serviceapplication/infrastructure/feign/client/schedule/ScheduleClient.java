package io.github.v1serviceapplication.infrastructure.feign.client.schedule;

import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.IsHomecomingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;

@FeignClient(name = "scheduleClient", url = "${service.scheme}://${service.schedule.host}")
public interface ScheduleClient {
    @GetMapping("/school/is-homecoming")
    IsHomecomingResponse queryIsHomecomingDay(@RequestParam("date") LocalDate date);
}
