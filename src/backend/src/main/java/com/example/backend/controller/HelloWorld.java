package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping(value = "/hello", produces = {"application/json;charset=UTF-8"})
    public String hello() {
        return "Hello World";
    }
}
