package com.endava.webapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Setter
public class EmployeeResponse {
    private int employeeId;
    private String firstName;
    private String lastName;
    private int departmentId;
    private String email;
    private String phoneNumber;
    private BigDecimal salary;
}
