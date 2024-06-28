package com.siyu.service_blog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.siyu.common_static.enums.VisitorLoggerEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {
    VisitorLoggerEnum value() default VisitorLoggerEnum.DEFAULT;
}
