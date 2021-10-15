package com.endava.webapp.controller;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.dto.EmployeeResponse;
import com.endava.webapp.service.EmployeeService;
import com.endava.webapp.service.validation.CheckEmailUniqueness;
import com.endava.webapp.service.validation.CheckPhoneUniqueness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    public static final String EMPLOYEES = "/employees";
    public static final String EMPLOYEE_ID = EMPLOYEES + "/{id}";

    @GetMapping(EMPLOYEES)
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        log.info("Get all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping(EMPLOYEE_ID)
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable final int id) {
        log.info("Get employee by id: " + id);
        val employee = employeeService.getEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping(path = EMPLOYEES, consumes = {"application/json"})
    public ResponseEntity<EmployeeResponse> addNewEmployee(
            @Valid @CheckEmailUniqueness @CheckPhoneUniqueness @RequestBody final EmployeeRequest employee) {
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @PutMapping(path = EMPLOYEE_ID, consumes = {"application/json"})
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @Valid @RequestBody final EmployeeRequest employee, @PathVariable final int id) {
        log.info("Update employee details, whose id is: " + id);
        return ResponseEntity.ok(employeeService.updateEmployee(employee, id));
    }

    @DeleteMapping(EMPLOYEE_ID)
    public ResponseEntity<?> deleteEmployee(@PathVariable final int id) {
        employeeService.deleteEmployee(id);
        log.info("Deleting the employee, whose id is: " + id);
        return ResponseEntity.noContent().build();
    }


}
