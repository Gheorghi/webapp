package com.endava.webapp.repository;

import com.endava.webapp.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee addEmployee(Employee employee);

    Employee updateEmployee(Employee employee, int id);

    Employee getEmployeeById(int id);

    List<Employee> getEmployees();

    void deleteEmployee(int id);

    boolean isEmailUnique(String email);

    boolean isPhoneUnique(String phone);
}
