package com.tqi.course.controller;

import com.tqi.course.model.Employee;
import com.tqi.course.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    private List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    private Employee getById(@PathVariable("id") int id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    private void saveOrUpdate(@RequestBody Employee employee) {
        employeeService.saveOrUpdate(employee);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable("id") int id) {
        employeeService.delete(id);
    }

}