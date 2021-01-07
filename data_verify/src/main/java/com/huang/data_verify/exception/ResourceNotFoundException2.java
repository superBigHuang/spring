package com.huang.data_verify.exception;

import com.huang.data_verify.controller.ExceptionController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResponseStatusException
 */
@ControllerAdvice(assignableTypes = {ExceptionController.class})
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException2 extends RuntimeException{


    public ResourceNotFoundException2(){
    }

    public ResourceNotFoundException2(String message) {
        super(message);
    }
}
