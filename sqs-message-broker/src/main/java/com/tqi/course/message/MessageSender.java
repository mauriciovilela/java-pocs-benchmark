package com.tqi.course.message;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import io.awspring.cloud.messaging.core.QueueMessageChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSender {

    private static final String QUEUE_NAME = "http://localhost:9324/queue/test-queue";

    @Autowired
    private AmazonSQSAsync amazonSQSAsync;

    public void sendMessageAsync(final String messagePayload) {
        MessageChannel messageChannel = new QueueMessageChannel(amazonSQSAsync, QUEUE_NAME);
        messageChannel.send(MessageBuilder.withPayload(messagePayload).build());
        log.info("Message sent={}", messagePayload);
    }

}