package com.endava.webapp.repository.exceptions;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(final String message) {
        super(message);
    }
}
