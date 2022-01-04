package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the arxiv.org api cannot be reached.
 *
 * @author Lukas Metzner
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ArxivNotAvailableException extends RuntimeException {
    public ArxivNotAvailableException(String s) {
    }
}
