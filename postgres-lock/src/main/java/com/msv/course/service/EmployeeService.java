package com.msv.course.service;

import com.msv.course.model.Employee;
import com.msv.course.model.Log;
import com.msv.course.repository.EmployeeRepository;
import com.msv.course.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogRepository logRepository;

    private static final String PENDING = "P";
    private static final String DONE = "D";
    private static final int TOTAL_PER_LOT = 1000;

    @Transactional
    public void processWithSkipLock() {
        List<Employee> listToProcess = findByStatusSkipLock(PENDING).stream().toList();
        while (!listToProcess.isEmpty()) {
            listToProcess.forEach(this::processItem);
            listToProcess = findByStatusSkipLock(PENDING).toList();
        }
    }

    @Transactional //(propagation = Propagation.REQUIRES_NEW)
    public void processWithoutSkipLock() {
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
        employee.setUpdateDate(LocalDateTime.now());
        log.info("Processed={}", employee.getId());
    }

    public Page<Employee> findByStatusSkipLock(String status) {
        return employeeRepository.findByStatusSkipLock(status, PageRequest.of(0, TOTAL_PER_LOT));
    }

    public Page<Employee> findByStatus(String status) {
        return employeeRepository.findByStatus(status, PageRequest.of(0, TOTAL_PER_LOT));
    }

}