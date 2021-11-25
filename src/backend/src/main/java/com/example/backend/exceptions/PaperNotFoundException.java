package com.example.backend.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class PaperNotFoundException extends RuntimeException{
    public PaperNotFoundException(String message){
        super(message);
    }

}
