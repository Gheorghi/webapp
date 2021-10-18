package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.dto.EmployeeResponse;
import com.endava.webapp.model.Department;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.DepartmentRepository;
import com.endava.webapp.repository.EmployeeRepository;
import com.endava.webapp.service.validation.exceptions.UniqueConstraintException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

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
    public EmployeeResponse addEmployee(final EmployeeRequest employeeRequest) {
        val employee = mapRequestToEmployee(employeeRequest);
        val savedEmployee = employeeRepository.addEmployee(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(final EmployeeRequest employeeRequest, final int id) {
        val employee = employeeRepository.getEmployeeById(id);
        checkAndUpdate(employeeRequest, employee);
        val updatedEmployee = employeeRepository.updateEmployee(employee, id);
        return mapToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(final int id) {
        employeeRepository.deleteEmployee(id);
    }

    private EmployeeResponse mapToResponse(final Employee employee) {
        return mapEmployeeToResponse(employee);
    }

    private List<EmployeeResponse> mapToResponse(final List<Employee> employee) {
        return employee.stream()
                .map(empl -> getEmployee(empl.getEmployeeId()))
                .collect(Collectors.toList());
    }

    private final Employee mapRequestToEmployee(final EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeRequest.getEmployeeId());
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setDepartmentId(mapToDepartment(employeeRequest));
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setSalary(employeeRequest.getSalary());
        return employee;
    }

    private final EmployeeResponse mapEmployeeToResponse(final Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(employee.getEmployeeId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setDepartmentId(employee.getDepartmentId().getId());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setPhoneNumber(employee.getPhoneNumber());
        employeeResponse.setSalary(employee.getSalary());
        return employeeResponse;
    }

    private final Department mapToDepartment(final EmployeeRequest employeeRequest) {
        return departmentRepository.getDepartmentById(employeeRequest.getDepartmentId());
    }

    private final void checkAndUpdate(final EmployeeRequest employeeRequest, final Employee employee) {
        if (!employee.getFirstName().equals(employeeRequest.getFirstName())) {
            employee.setFirstName(employeeRequest.getFirstName());
        }
        if (!employee.getLastName().equals(employeeRequest.getLastName())) {
            employee.setLastName(employeeRequest.getLastName());
        }
        if (!employee.getDepartmentId().equals(employeeRequest.getDepartmentId())) {
            employee.setDepartmentId(mapToDepartment(employeeRequest));
        }
        if (!employee.getEmail().equals(employeeRequest.getEmail())) {
            if (employeeRepository.isEmailUnique(employeeRequest.getEmail())) {
                employee.setEmail(employeeRequest.getEmail());
            } else
                throw new UniqueConstraintException("<Email is already registered>");
        }
        if (!employee.getPhoneNumber().equals(employeeRequest.getPhoneNumber())) {
            if (employeeRepository.isPhoneUnique(employeeRequest.getPhoneNumber())) {
                employee.setPhoneNumber(employeeRequest.getPhoneNumber());
            } else
                throw new UniqueConstraintException("<Phone number is already registered>");
        }
        if (!employee.getSalary().equals(employeeRequest.getSalary())) {
            employee.setSalary(employeeRequest.getSalary());
        }
    }
}
