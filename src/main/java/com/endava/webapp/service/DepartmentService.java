package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentRequest;
import com.endava.webapp.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartment(int id);

    DepartmentResponse addDepartment(DepartmentRequest department);

    DepartmentResponse updateDepartment(int id, DepartmentRequest department);

    void deleteDepartment(int id);
}
