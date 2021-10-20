package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.dto.EmployeeResponse;
import com.endava.webapp.model.Department;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.DepartmentRepository;
import com.endava.webapp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return mapToResponse(employeeRepository.findAll());
    }

    @Override
    public EmployeeResponse addEmployee(final EmployeeRequest employeeRequest) {
        val employees = getAllEmployees();
        validateEmailUniqueness(employees, employeeRequest);
        validatePhoneUniqueness(employees, employeeRequest);
        val employee = mapRequestToEmployee(employeeRequest);
        val savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(final EmployeeRequest employeeRequest, final int id) {
        val employees = getAllEmployees();
        validateEmailUniqueness(employees, employeeRequest);
        validatePhoneUniqueness(employees, employeeRequest);
        val employee = employeeRepository.getEmployeeById(id);
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

    private EmployeeResponse mapEmployeeToResponse(final Employee employee) {
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

    private void validateEmailUniqueness
            (final List<EmployeeResponse> employees, final EmployeeRequest employeeRequest) {
        employees.forEach(employee -> {
            if (employee.getEmail().equalsIgnoreCase(employeeRequest.getEmail()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone already registered, use another one");
        });
    }

    private void validatePhoneUniqueness
            (final List<EmployeeResponse> employees, final EmployeeRequest employeeRequest) {
        employees.forEach(employee -> {
            if (employee.getPhoneNumber().equalsIgnoreCase(employeeRequest.getPhoneNumber()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone already registered, use another one");
        });
    }
}
