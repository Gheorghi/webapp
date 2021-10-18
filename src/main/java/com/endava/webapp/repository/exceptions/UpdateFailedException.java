package com.endava.webapp.repository.exceptions;

public class UpdateFailedException extends RuntimeException{
    public UpdateFailedException(final String message) {
        super(message);
    }
}
