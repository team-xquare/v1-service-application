package io.github.v1serviceapplication.infrastructure.feign.client.user;

import io.github.v1serviceapplication.infrastructure.feign.client.dto.request.UserInfoRequest;
import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponse;
import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponseElement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "userClient", url = "${service.scheme}://${service.user.host}")
public interface UserClient {

    @PostMapping("/users/id")
    UserInfoResponse queryUserInfoByUserId(@Valid @RequestBody UserInfoRequest request);

    @GetMapping("/users/all")
    UserInfoResponse queryAllUser();

    @GetMapping("/users/id/{userId}")
    UserInfoResponseElement queryUserInfo(@PathVariable("userId") UUID userId);
}
