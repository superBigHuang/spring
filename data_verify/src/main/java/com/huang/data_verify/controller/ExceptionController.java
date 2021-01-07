package com.huang.data_verify.controller;

import com.huang.data_verify.exception.ResourceNotFoundException;
import com.huang.data_verify.exception.ResourceNotFoundException2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class ExceptionController {

    @GetMapping("/illegalArgumentException")
    public void throwException() {
        throw new IllegalArgumentException();
    }

    @GetMapping("/resourceNotFoundException")
    public void throwException2() {
        throw new ResourceNotFoundException();
    }

    @GetMapping("/resourceNotFoundException2")
    public void throwException3() {
        throw new ResourceNotFoundException2("Sorry, the resourse not found!");
    }

    @GetMapping("/resourceNotFoundException3")
    public void throwException4() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"message",new ResourceNotFoundException());
    }
}
