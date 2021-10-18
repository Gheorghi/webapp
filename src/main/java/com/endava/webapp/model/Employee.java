package com.endava.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private int departmentId;
    private String email;
    private String phoneNumber;
    private BigDecimal salary;
}
