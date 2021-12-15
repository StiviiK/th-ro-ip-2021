package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class KeywordServiceNotAvailableException extends RuntimeException {
    public KeywordServiceNotAvailableException(String message) {
        super(message);
    }
}
