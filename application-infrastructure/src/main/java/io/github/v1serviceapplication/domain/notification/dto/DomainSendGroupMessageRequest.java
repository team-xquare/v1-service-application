package io.github.v1serviceapplication.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainSendGroupMessageRequest {

    private String topic;

    private String content;

    private String threadId;
}
