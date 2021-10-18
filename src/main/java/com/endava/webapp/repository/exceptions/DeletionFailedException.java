package com.endava.webapp.repository.exceptions;

public class DeletionFailedException extends RuntimeException{
    public DeletionFailedException(final String message) {
        super(message);
    }
}
