package com.endava.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {
    private int employeeId;

    @NotBlank(message = "Employee's first name is mandatory")
    private String firstName;

    @NotBlank(message = "Employee's last name is mandatory")
    private String lastName;

    private int departmentId;

    @NotBlank(message = "Employee's email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Employee's phone number is mandatory")
    private String phoneNumber;

    @DecimalMin(value = "1.0", message = "Employee's salary should be greater than 0.99 kg of gold")
    private BigDecimal salary;
}
