package com.msv.course.consumer;

import com.msv.course.config.PgTopicEnum;
import com.msv.course.service.OptimisticLockService;
import com.msv.course.service.OrdersService;
import com.msv.course.service.PessimistLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.postgresql.PGNotification;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer implements Consumer<PGNotification> {

    private final OrdersService ordersService;
    private final PessimistLockService pessimistLockService;
    private final OptimisticLockService optimisticLockService;

    @Override
    public void accept(PGNotification message) {
        log.info("Received={} PID={}", message.getParameter(), message.getPID());
        Long id = loadIdFromJson(message);
        if (message.getName().equalsIgnoreCase(PgTopicEnum.PESSIMIST_LOCK.getValue())) {
            pessimistLockService.processWithPessimisticLock(id);
        } else if (message.getName().equalsIgnoreCase(PgTopicEnum.OPTIMISTIC_LOCK.getValue())) {
            optimisticLockService.processWithOptimisticLock(id);
        } else if (message.getName().equalsIgnoreCase(PgTopicEnum.DEFAULT_TOPIC.getValue())) {
            ordersService.processWithTopic(id);
        } else {
            log.info("topic is not found");
        }
    }

    private Long loadIdFromJson(PGNotification message) {
        return new JSONObject(message.getParameter()).getLong("id");
    }

}