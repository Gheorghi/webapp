package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.dto.EmployeeResponse;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.EmployeeRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepositoryImpl employeeRepository;

    @Override
    public EmployeeResponse getEmployee(final int id) {
        val employee = employeeRepository.getEmployeeById(id);
        return mapToResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return mapToResponse(employeeRepository.getEmployees());
    }

    @Override
    public EmployeeResponse addEmployee(final EmployeeRequest employee) {
        return mapToResponse(employeeRepository.addEmployee(mapRequestToEmployee(employee)));
    }

    @Override
    public EmployeeResponse updateEmployee(final EmployeeRequest employee, final int id) {
        return mapToResponse(employeeRepository.updateEmployee(mapRequestToEmployee(employee), id));
    }

    @Override
    public void deleteEmployee(final int id) {
        employeeRepository.deleteEmployee(id);
    }

    private EmployeeResponse mapToResponse(final Employee employee) {
        val employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }

    private List<EmployeeResponse> mapToResponse(final List<Employee> employee) {
        return employee.stream()
                .map(empl -> getEmployee(empl.getEmployeeId()))
                .collect(Collectors.toList());
    }

    private final Employee mapRequestToEmployee(final EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        employee.setEmployeeId(employeeRequest.getEmployeeId());
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setDepartmentId(employeeRequest.getDepartmentId());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setSalary(employeeRequest.getSalary());
        return employee;
    }
}
