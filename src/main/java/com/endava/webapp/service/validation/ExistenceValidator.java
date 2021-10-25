package com.endava.webapp.service.validation;

import com.endava.webapp.repository.EmployeeRepository;
import com.endava.webapp.service.exceptions.UniqueConstraintException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExistenceValidator {

    private final EmployeeRepository employeeRepository;

    public void validatePhone(final String phone) {
        if (employeeRepository.existsByPhoneNumber(phone)) {
            throw new UniqueConstraintException(HttpStatus.CONFLICT, phone + " already registered, use another one");
        }
    }

    public void validateEmail(final String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new UniqueConstraintException(HttpStatus.CONFLICT, email + " already registered, use another one");
        }
    }
}
