package io.github.v1serviceapplication.domain.notification;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.v1serviceapplication.domain.notification.dto.DomainSendGroupMessageRequest;
import io.github.v1serviceapplication.domain.notification.dto.DomainSendMessageRequest;
import io.github.v1serviceapplication.domain.notification.dto.DomainSendSpecificGroupRequest;
import io.github.v1serviceapplication.error.JsonConvertException;
import io.github.v1serviceapplication.notification.NotificationSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class NotificationAdapter implements NotificationSpi {

    private final AmazonSQS amazonSQS;
    private final ObjectMapper objectMapper;
    private static final String NOTIFICATION_FIFO = "notification.fifo";
    private static final String NOTIFICATION_GROUP_FIFO = "group-notification.fifo";
    private static final String NOTIFICATION_SPECIFIC_GROUP_FIFO = "specific-group-notification.fifo";


    @Override
    public void sendNotification(UUID userId, String topic, String content, String threadId) {
        DomainSendMessageRequest domainSendMessageRequest = new DomainSendMessageRequest(
                userId,
                topic,
                content,
                threadId
        );

        sendSqsMessage(NOTIFICATION_FIFO, convertToJsonString(domainSendMessageRequest));
    }

    @Override
    public void sendGroupNotification(String topic, String content, String threadId) {
        DomainSendGroupMessageRequest domainSendGroupMessageRequest = new DomainSendGroupMessageRequest(
                topic,
                content,
                threadId
        );

        sendSqsMessage(NOTIFICATION_GROUP_FIFO, convertToJsonString(domainSendGroupMessageRequest));
    }

    @Override
    public void sendSpecificGroupNotification(List<UUID> userIdList, String topic, String content, String threadId) {
        DomainSendSpecificGroupRequest domainSendSpecificGroupRequest = new DomainSendSpecificGroupRequest(
                userIdList,
                topic,
                content,
                threadId
        );

        sendSqsMessage(NOTIFICATION_SPECIFIC_GROUP_FIFO, convertToJsonString(domainSendSpecificGroupRequest));
    }

    private void sendSqsMessage(String queueName, String content) {
        String name = amazonSQS.getQueueUrl(queueName).getQueueUrl();
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(name)
                .withMessageBody(content)
                .withMessageGroupId("feed")
                .withMessageDeduplicationId(UUID.randomUUID().toString())
                .withMessageAttributes(
                        Map.of(
                                "Content-Type", new MessageAttributeValue()
                                        .withDataType("String")
                                        .withStringValue("application/json")
                        )
                );

        amazonSQS.sendMessage(sendMessageRequest);
    }

    private String convertToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw JsonConvertException.EXCEPTION;
        }
    }
}
