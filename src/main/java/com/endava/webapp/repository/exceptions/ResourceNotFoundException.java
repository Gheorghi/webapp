package com.endava.webapp.repository.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException(HttpStatus notFound, String errorMessage) {
        super(notFound, errorMessage);
    }
}
