package com.endava.webapp.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private BigDecimal salary;
    private BigDecimal commissionPct;
    private int managerId;
    private int departmentId;
}
