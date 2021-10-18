package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentRequest;
import com.endava.webapp.dto.DepartmentResponse;
import com.endava.webapp.model.Department;
import com.endava.webapp.repository.DepartmentRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepositoryImpl departmentRepository;

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return mapToResponse(departmentRepository.getDepartments());
    }

    @Override
    public DepartmentResponse getDepartment(final int id) {
        val department = departmentRepository.getDepartmentById(id);
        return mapToResponse(department);
    }

    @Override
    public DepartmentResponse addDepartment(final DepartmentRequest department) {
        return mapToResponse(departmentRepository
                .addDepartment(
                        mapRequestToEmployee(department)));
    }

    @Override
    public DepartmentResponse updateDepartment(final DepartmentRequest department, final int id) {
        return mapToResponse(departmentRepository
                .updateDepartment(
                        mapRequestToEmployee(department), id));
    }

    @Override
    public void deleteDepartment(final int id) {
        departmentRepository.deleteDepartment(id);
    }

    private DepartmentResponse mapToResponse(final Department department) {
        val departmentResponse = new DepartmentResponse();
        BeanUtils.copyProperties(department, departmentResponse);
        return departmentResponse;
    }

    private List<DepartmentResponse> mapToResponse(final List<Department> department) {
        return department.stream()
                .map(dep -> getDepartment(dep.getId()))
                .collect(Collectors.toList());
    }

    private final Department mapRequestToEmployee(final DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setId(departmentRequest.getId());
        department.setName(departmentRequest.getName());
        department.setLocation(departmentRequest.getLocation());
        return department;
    }
}
