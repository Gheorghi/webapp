package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.dto.EmployeeResponse;
import com.endava.webapp.model.Department;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.DepartmentRepository;
import com.endava.webapp.repository.EmployeeRepository;
import com.endava.webapp.service.exceptions.UniqueConstraintException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

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
        return mapToResponse(employeeRepository.findAll());
    }

    @Override
    public EmployeeResponse addEmployee(final EmployeeRequest employeeRequest) {
        validateEmailUniqueness(employeeRequest.getEmail());
        validatePhoneUniqueness(employeeRequest.getPhoneNumber());
        val employee = mapRequestToEmployee(employeeRequest);
        val savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(final int id, final EmployeeRequest employeeRequest) {
        val employee = employeeRepository.getEmployeeById(id);
        validateEmailUniqueness(employeeRequest.getEmail());
        validatePhoneUniqueness(employeeRequest.getPhoneNumber());
        checkAndUpdate(employeeRequest, employee);
        val updatedEmployee = employeeRepository.save(employee);
        return mapToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(final int id) {
        val employee = employeeRepository.getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    private EmployeeResponse mapToResponse(final Employee employee) {
        return mapEmployeeToResponse(employee);
    }

    private List<EmployeeResponse> mapToResponse(final List<Employee> employee) {
        return employee.stream()
                .map(empl -> getEmployee(empl.getEmployeeId()))
                .collect(Collectors.toList());
    }

    private Employee mapRequestToEmployee(final EmployeeRequest employeeRequest) {
        val employee = new Employee();
        employee.setEmployeeId(employeeRequest.getEmployeeId());
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setDepartmentId(mapToDepartment(employeeRequest));
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setSalary(employeeRequest.getSalary());
        return employee;
    }

    private EmployeeResponse mapEmployeeToResponse(final Employee employee) {
        val employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(employee.getEmployeeId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setDepartmentId(employee.getDepartmentId().getId());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setPhoneNumber(employee.getPhoneNumber());
        employeeResponse.setSalary(employee.getSalary());
        return employeeResponse;
    }

    private Department mapToDepartment(final EmployeeRequest employeeRequest) {
        return departmentRepository.getById(employeeRequest.getDepartmentId());
    }

    private void checkAndUpdate(final EmployeeRequest employeeRequest, final Employee employee) {
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
            employee.setEmail(employeeRequest.getEmail());
        }
        if (!employee.getPhoneNumber().equals(employeeRequest.getPhoneNumber())) {
            employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        }
        if (!employee.getSalary().equals(employeeRequest.getSalary())) {
            employee.setSalary(employeeRequest.getSalary());
        }
    }

    private void validateEmailUniqueness(final String email) {
        if (existsEmail(email)) {
            throw new UniqueConstraintException(HttpStatus.CONFLICT, "Phone already registered, use another one");
        }
    }

    private void validatePhoneUniqueness(final String phoneNumber) {
        if (existsPhoneNumber(phoneNumber)) {
            throw new UniqueConstraintException(HttpStatus.CONFLICT, "Phone already registered, use another one");
        }
    }

    private boolean existsEmail(final String email) {
        var employee = new Employee();
        employee.setEmail(email);
        val employeeExample = Example.of(employee, getMathcer(email));
        return employeeRepository.exists(employeeExample);
    }

    private boolean existsPhoneNumber(final String phoneNumber) {
        var employee = new Employee();
        employee.setPhoneNumber(phoneNumber);
        val employeeExample = Example.of(employee, getMathcer(phoneNumber));
        return employeeRepository.exists(employeeExample);
    }

    private ExampleMatcher getMathcer(final String target) {
        return ExampleMatcher.matching()
                .withIgnorePaths("employeeId")
                .withMatcher(target, ignoreCase());
    }
}
