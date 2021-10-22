package com.endava.webapp.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UniqueConstraintException extends ResponseStatusException {
    public UniqueConstraintException(HttpStatus notFound, String errorMessage) {
        super(notFound, errorMessage);
    }
}
