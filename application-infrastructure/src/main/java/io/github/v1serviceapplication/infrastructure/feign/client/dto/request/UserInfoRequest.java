package io.github.v1serviceapplication.infrastructure.feign.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class UserInfoRequest {
    private List<UUID> userIds;
}
