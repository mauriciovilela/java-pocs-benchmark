package com.msv.course.controller;

import com.msv.course.config.PgTopicEnum;
import com.msv.course.service.NotifierService;
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