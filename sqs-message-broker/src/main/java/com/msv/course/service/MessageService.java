package com.msv.course.service;

import com.msv.course.message.MessageSender;
import com.msv.course.model.Message;
import com.msv.course.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSender messageSender;
    private final MessageRepository messageRepository;
    private final ProducerTemplate template;

    @Value("${queue-test-1}")
    private String queueTest1;

    public void sendTest1() {
        String randomed = "queue-1-" + RandomStringUtils.randomAlphanumeric(10);
        messageSender.sendMessage(randomed, queueTest1);
    }

    public void sendTest2() {
        String randomed = "queue-2-" + RandomStringUtils.randomAlphanumeric(10);
        template.sendBody("direct:sendMessage", randomed);
    }

    public void processMessageQueue(String message) {
        messageRepository.save(Message.builder()
                .text(message)
                .creationDate(LocalDateTime.now())
                .build());
    }

}