package com.endava.webapp.service.validation;

import com.endava.webapp.dto.EmployeeRequest;
import com.endava.webapp.repository.EmployeeRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueEmailValidator
        implements ConstraintValidator<CheckEmailUniqueness, EmployeeRequest> {
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(
            final EmployeeRequest employeeRequest, final ConstraintValidatorContext constraintValidatorContext) {
        return employeeRepository.isInColumn("EMAIL", employeeRequest.getEmail());
    }
}
