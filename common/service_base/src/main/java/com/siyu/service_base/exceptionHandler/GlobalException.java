package com.siyu.service_base.exceptionHandler;

import com.siyu.common_static.enums.ExceptionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class GlobalException extends RuntimeException{
    
    private String message;

    private Integer code;

    public GlobalException(ExceptionEnum exceptionEnum) {
        this.message = exceptionEnum.getMessage();
        this.code = exceptionEnum.getCode();
    }
    public GlobalException(ExceptionEnum exceptionEnum, String tips) {
        this.message = exceptionEnum.getMessage() + "[" + tips + "]";
        this.code = exceptionEnum.getCode();
    }
}
