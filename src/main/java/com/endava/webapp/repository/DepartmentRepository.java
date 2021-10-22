package com.endava.webapp.repository;

import com.endava.webapp.model.Department;
import com.endava.webapp.repository.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    default Department getDepartmentById(final Integer id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Department Not Found"));
    }
}
