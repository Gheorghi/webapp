package com.endava.webapp.repository;

import com.endava.webapp.model.Department;

import java.util.List;

public interface DepartmentRepository {
    Department getDepartmentById(int id);

    List<Department> getDepartments();

    Department addDepartment(Department department);

    Department updateDepartment(Department department, int id);

    void deleteDepartment(int id);
}
