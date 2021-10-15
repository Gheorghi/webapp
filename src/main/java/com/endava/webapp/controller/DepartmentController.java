package com.endava.webapp.controller;

import com.endava.webapp.dto.DepartmentRequest;
import com.endava.webapp.dto.DepartmentResponse;
import com.endava.webapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    public static final String DEPARTMENTS = "/departments";
    public static final String DEPARTMENT_ID = DEPARTMENTS + "/{id}";

    @GetMapping(DEPARTMENTS)
    public ResponseEntity<List<DepartmentResponse>> getDepartments() {
        log.info("Get all departments");
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping(DEPARTMENT_ID)
    public ResponseEntity<DepartmentResponse> getDepartment(@PathVariable final int id) {
        log.info("Get department by id " + id);
        val department = departmentService.getDepartment(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping(path = DEPARTMENTS, consumes = {"application/json"})
    public ResponseEntity<DepartmentResponse> addNewDepartment(@Valid @RequestBody final DepartmentRequest department) {
        return ResponseEntity.ok(departmentService.addDepartment(department));
    }

    @PutMapping(path = DEPARTMENT_ID, consumes = {"application/json"})
    public ResponseEntity<DepartmentResponse> updateDepartment(@Valid @RequestBody final DepartmentRequest department,
                                                               @PathVariable int id) {
        log.info("Update department details, which id is " + id);
        return ResponseEntity.ok(departmentService.updateDepartment(department, id));
    }

    @DeleteMapping(DEPARTMENT_ID)
    public ResponseEntity<?> deleteDepartment(@PathVariable final int id) {
        departmentService.deleteDepartment(id);
        log.info("Deleting the department, which id is " + id);
        return ResponseEntity.noContent().build();
    }
}
