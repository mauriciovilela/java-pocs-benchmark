package com.tqi.course.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PgTopicEnum {
    PESSIMIST_LOCK("topic_pessimist"),
    OPTIMISTIC_LOCK("topic_optimistic"),
    DEFAULT_TOPIC("topic_default"),
    NONE("none");
    @Getter
    private String value;
}
