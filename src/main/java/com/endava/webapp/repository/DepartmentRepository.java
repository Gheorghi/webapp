package com.endava.webapp.repository;

import com.endava.webapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    default Department getDepartmentById(final Integer id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found"));
    }
}
