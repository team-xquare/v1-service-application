package io.github.v1serviceapplication.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DomainSendMessageRequest {
    private UUID userId;

    private String topic;

    private String content;

    private String threadId;
}
