package com.endava.webapp.controller;

import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/get/{employee_id}")
    public Optional<Employee> getEmployee(@PathVariable(value = "employee_id") int id){
        Optional<Employee> employees = employeeRepository.getEmployeeById(id);
        return employees;
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }


}
