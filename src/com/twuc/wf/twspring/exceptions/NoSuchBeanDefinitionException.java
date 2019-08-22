package com.twuc.wf.twspring.exceptions;

public class NoSuchBeanDefinitionException extends Exception {
    public NoSuchBeanDefinitionException() {
    }
    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }
}
