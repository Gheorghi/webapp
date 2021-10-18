package com.endava.webapp.service.validation.exceptions;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(final String message) {
        super(message);
    }
}
