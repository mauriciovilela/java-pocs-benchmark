package com.msv.course.message;

import com.msv.course.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageReceiver {

    @Autowired
    private MessageService messageService;

    @SqsListener(value = "${queue-test-1}")
    public void processBillGenerationMessage(String message) {
        log.info("Message received={}", message);
        messageService.processMessageQueue(message);
    }

}