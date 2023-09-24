package com.msv.course.message;

import com.msv.course.service.MessageService;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageReceiver {

    @Autowired
    private MessageService messageService;

    @SqsListener(value = "test-queue")
    public void receiveStringMessage(final String message) {
        log.info("Message received={}", message);
        messageService.processMessageQueue(message);
    }

}