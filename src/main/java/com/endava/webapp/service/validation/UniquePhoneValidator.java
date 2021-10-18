package com.endava.webapp.service.validation;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniquePhoneValidator
        implements ConstraintValidator<CheckPhoneUniqueness, EmployeeRequest> {
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(
            final EmployeeRequest employeeRequest, final ConstraintValidatorContext constraintValidatorContext) {
        return employeeRepository.isPhoneUnique(employeeRequest.getPhoneNumber());
    }
}