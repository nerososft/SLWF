package com.twuc.wf.twspring.annotations;

import com.twuc.wf.twspring.simplehttpserver.contract.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseStatus {
    HttpStatus value() default HttpStatus.OK;
}
