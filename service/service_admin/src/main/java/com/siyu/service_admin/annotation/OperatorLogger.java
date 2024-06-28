package com.siyu.service_admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.siyu.common_static.enums.OperatorLoggerEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperatorLogger {
    OperatorLoggerEnum value() default OperatorLoggerEnum.DEFAULT;
}
