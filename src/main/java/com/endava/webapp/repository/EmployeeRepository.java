package com.endava.webapp.repository;

import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    default Employee getEmployeeById(final Integer id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Employee Not Found"));
    }

    boolean existsByPhoneNumber(final String phone);

    boolean existsByEmail(final String email);
}
