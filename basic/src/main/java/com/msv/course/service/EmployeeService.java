package com.msv.course.service;

import com.msv.course.model.Employee;
import com.msv.course.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
    }

    public void saveOrUpdate(Employee employee) {

        // default
        Employee newEmployee = new Employee();
        newEmployee.setId(employee.getId());
        newEmployee.setName(employee.getName());
        newEmployee.setCompany(employee.getCompany());
        newEmployee.setSalary(employee.getSalary());

        employeeRepository.save(employee);

        // builder
        Employee newEmployeeBuilder = Employee.builder()
            .name(employee.getName())
            .salary(employee.getSalary())
            .id(employee.getId())
            .company(employee.getCompany())
            .build();

        employeeRepository.save(newEmployeeBuilder);
    }

    public void delete(int id) {
        employeeRepository.deleteById(id);
    }
}