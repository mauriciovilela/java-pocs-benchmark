package com.tqi.course.controller;

import com.tqi.course.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Slf4j
@RequestMapping("/contingency")
public class ContingencyController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/process-postgres")
    private void processPostgres() {
        for (int i = 1; i <= 10; i++) {
            int idx = i;
            ExecutorService executor= Executors.newFixedThreadPool(1);
            executor.execute(() -> {
                log.info("Start thread {}", idx);
                employeeService.processPostgres();
                log.info("End thread {}", idx);
            });
            executor.shutdown();
        }
    }

}