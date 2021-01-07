package com.huang.data_verify.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常类型
 * 一般我们处理的都是 RuntimeException ，所以如果你需要自定义异常类型的话直接集成这个类就可以了。
 */
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    private String message;

    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
