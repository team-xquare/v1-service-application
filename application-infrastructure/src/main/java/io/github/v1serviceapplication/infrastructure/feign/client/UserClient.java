package io.github.v1serviceapplication.infrastructure.feign.client;

import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "userClient", url = "${service.scheme}://${service.user.host}")
public interface UserClient {

    @GetMapping("/users/id")
    UserInfoResponse queryUserInfoByUserId(@RequestParam("userId") List<UUID> userId);

    @GetMapping("/users/all")
    UserInfoResponse queryAllUser();
}
