package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

  EmployeeResponse getEmployee(final int id);

  List<EmployeeResponse> getAllEmployees();

  EmployeeResponse addEmployee(EmployeeRequest employee);

  EmployeeResponse updateEmployee(EmployeeRequest employee, int id);

  void deleteEmployee(int id);
}
