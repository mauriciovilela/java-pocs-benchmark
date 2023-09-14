package com.tqi.course.controller;

import com.tqi.course.config.PgTopicEnum;
import com.tqi.course.service.NotifierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
public class DefaultController {

    @Autowired
    private NotifierService notifierService;

    @PostMapping("/send/{topic}")
    public void createRecordAndNotify(@PathVariable PgTopicEnum topic) {
        notifierService.createRecordAndNotify(topic);
    }

}