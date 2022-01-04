package de.throsenheim.ip.spm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String hello() {
        return "Hello World";
    }
}
