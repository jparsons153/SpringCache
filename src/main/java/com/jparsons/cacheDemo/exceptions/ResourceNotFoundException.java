package com.jparsons.cacheDemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus here ensures the NOT FOUND status is returned.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    // Create a constructor with the incoming message and call super to invoke the RuntimeException object
    public ResourceNotFoundException(String message) {
        super(message);
    }
}