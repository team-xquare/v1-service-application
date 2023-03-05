package io.github.v1serviceapplication.infrastructure.feign.client.point;

import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.PointStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "pointClient", url = "${service.scheme}://${service.point.host}")
public interface PointClient {

    @GetMapping("/points/student/{student-id}")
    PointStatusResponse queryPointStatusByUserId(@PathVariable("student-id") UUID userId);
}
