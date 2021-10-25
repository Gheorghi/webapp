package com.endava.webapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Setter
@Entity(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_Id")
    private int employeeId;

    @Column(name = "first_Name")
    private String firstName;

    @Column(name = "last_Name")
    private String lastName;

    @ManyToOne()
    @JoinColumn(name = "department_Id")
    private Department departmentId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_Number", unique = true)
    private String phoneNumber;

    @Column(name = "salary")
    @Min(1)
    private BigDecimal salary;
}
