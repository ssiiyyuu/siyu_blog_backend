package com.siyu.service_base.exceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siyu.service_base.result.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result Exception(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        log.warn(stringWriter.toString());
        return Result.error();
    }

    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public Result GlobalExceptionException(GlobalException e) {
        return Result.createUnsuccessResult()
                     .code(e.getCode())
                     .message(e.getMessage());
    }

}
