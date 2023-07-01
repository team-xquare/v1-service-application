package io.github.v1serviceapplication.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DomainSendSpecificGroupRequest {
    private List<UUID> userIdList;

    private String category;

    private String content;

    private String threadId;
}
