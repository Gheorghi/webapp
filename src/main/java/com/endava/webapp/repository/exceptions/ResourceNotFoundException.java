package com.endava.webapp.repository.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
