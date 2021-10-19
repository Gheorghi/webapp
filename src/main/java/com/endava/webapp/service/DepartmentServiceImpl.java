package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentRequest;
import com.endava.webapp.dto.DepartmentResponse;
import com.endava.webapp.model.Department;
import com.endava.webapp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

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
    public DepartmentResponse addDepartment(final DepartmentRequest departmentRequest) {
        val department = mapRequestToDepartment(departmentRequest);
        val savedDepartment = departmentRepository.addDepartment(department);
        return mapToResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse updateDepartment(final DepartmentRequest departmentRequest, final int id) {
        val department = departmentRepository.getDepartmentById(id);
        checkAndUpdate(departmentRequest, department);
        val savedDepartment = departmentRepository.updateDepartment(department, id);
        return mapToResponse(savedDepartment);
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

    private Department mapRequestToDepartment(final DepartmentRequest departmentRequest) {
        val department = new Department();
        BeanUtils.copyProperties(departmentRequest, department);
        return department;
    }

    private void checkAndUpdate(final DepartmentRequest departmentRequest, final Department department) {
        if (!department.getName().equals(departmentRequest.getName())) {
            department.setName(departmentRequest.getName());
        }
        if (!department.getLocation().equals(departmentRequest.getLocation())) {
            department.setLocation(departmentRequest.getLocation());
        }
    }
}
