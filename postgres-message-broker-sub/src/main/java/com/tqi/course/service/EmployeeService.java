package com.tqi.course.service;

import com.tqi.course.model.Employee;
import com.tqi.course.model.Log;
import com.tqi.course.repository.EmployeeRepository;
import com.tqi.course.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogRepository logRepository;

    private static final String PENDING = "P";
    private static final String DONE = "D";

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processPostgres() {
        List<Employee> listToProcess = findByStatus(PENDING).stream().toList();
        while (!listToProcess.isEmpty()) {
            listToProcess.forEach(this::processItem);
            listToProcess = findByStatus(PENDING).toList();
        }
    }

    private void processItem(Employee employee) {
        logRepository.save(Log.builder()
                .creationDate(LocalDateTime.now())
                .text(String.format("id=%d employee=%s", employee.getId(), employee.getName()))
                .build());
        employee.setStatus(DONE);
    }

    public Page<Employee> findByStatus(String status) {
        Pageable pageable = PageRequest.of(0, 1000);
        return employeeRepository.findByStatus(status, pageable);
    }

}