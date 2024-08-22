package com.msv.course.controller;

import com.msv.course.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
public class DefaultController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/test-1")
    public void test1() {
        messageService.sendTest1();
    }

}