package com.endava.webapp.repository;

import com.endava.webapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    default Employee getEmployeeById(Integer id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found"));
    }
}
