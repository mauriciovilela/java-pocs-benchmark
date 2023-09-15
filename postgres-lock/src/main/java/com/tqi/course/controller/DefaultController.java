package com.tqi.course.controller;

import com.tqi.course.service.EmployeeService;
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
    private EmployeeService employeeService;

    @PostMapping("/process-skip-lock")
    private void processSkipLock() {
        employeeService.processWithSkipLock();
    }

    @PostMapping("/process")
    private void process() {
        employeeService.processWithoutSkipLock();
    }

}