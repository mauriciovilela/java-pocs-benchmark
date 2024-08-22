package com.msv.course.message;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSender {

    private final AmazonSQSAsync amazonSQSAsync;

    public void sendMessage(Object message, String queue) {
        try {
            SendMessageRequest sendMessageRequest = new SendMessageRequest();
            sendMessageRequest.setMessageBody(message.toString());
            sendMessageRequest.setQueueUrl("http://localhost:9324/queue/" + queue);
            SendMessageResult sendMessageResult = amazonSQSAsync.sendMessage(sendMessageRequest);
            log.info("Message sent successfully={}", sendMessageResult);
        } catch (Exception e) {
            log.info("Error send message={}", e.getMessage(), e);
        }
    }

//    public void sendMessage(Object message, String queue) {
//        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
//                .queueUrl("http://localhost:9324/queue/" + queue)
//                .messageBody(message.toString())
//                .build();
//        sendMessage(sendMsgRequest);
//    }
//
//    public void sendMessage(SendMessageRequest sendMessageRequest) {
//        sqsAsyncClient.sendMessage(sendMessageRequest).whenComplete((sendResult, throwable) -> {
//            if (Objects.nonNull(throwable)) {
//                log.error("Error on sending message={}", sendResult, throwable);
//            } else {
//                log.info("Message sent successfully={}", sendMessageRequest.messageBody());
//            }
//        });
//    }

}