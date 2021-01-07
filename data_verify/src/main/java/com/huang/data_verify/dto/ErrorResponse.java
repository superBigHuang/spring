package com.huang.data_verify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.OutputBuffer;

/**
 * 异常信息实体类
 * 主要用于包装异常信息
 */
@Data
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String errorTypeName;

    public ErrorResponse(Exception e) {
        this(e.getClass().getName(),e.getMessage());
    }


    public ErrorResponse(String errorTypeName, String message) {
        this.errorTypeName = errorTypeName;
        this.message = message;
    }
}
