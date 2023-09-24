package com.msv.course.service;

import com.msv.course.config.PgTopicEnum;
import com.msv.course.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@RequiredArgsConstructor
public class NotifierService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private PessimistLockService pessimistLockService;
    @Autowired
    private OptimisticLockService optimisticLockService;

    public void createRecordAndNotify(PgTopicEnum topicEnum) {
        if (topicEnum.equals(PgTopicEnum.PESSIMIST_LOCK)) {
            notify(pessimistLockService.create().getId(), topicEnum);
        } else if (topicEnum.equals(PgTopicEnum.OPTIMISTIC_LOCK)) {
            notify(optimisticLockService.create().getId(), topicEnum);
        } else if (topicEnum.equals(PgTopicEnum.DEFAULT_TOPIC)) {
            notify(ordersService.create().getId(), topicEnum);
        } else if (topicEnum.equals(PgTopicEnum.NONE)) {
            Order order = ordersService.create();
            ordersService.processWithTopic(order.getId());
        } else {
            log.info("topic is not found");
        }
    }

    public void notify(Long id, PgTopicEnum topicEnum) {
        jdbcTemplate.execute("NOTIFY " + topicEnum.getValue() + ", '" + buildJsonPayload(id) + "'");
        log.info("notify topic={} id={}", topicEnum.getValue(), id);
    }

    private JSONObject buildJsonPayload(Long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        return jsonObject;
    }

}