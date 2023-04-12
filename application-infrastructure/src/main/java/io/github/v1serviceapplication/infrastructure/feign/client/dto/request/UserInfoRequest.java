package io.github.v1serviceapplication.infrastructure.feign.client.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class UserInfoRequest {
    private List<UUID> userIds;
}
