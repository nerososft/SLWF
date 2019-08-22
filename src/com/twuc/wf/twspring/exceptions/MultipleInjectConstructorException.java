package com.twuc.wf.twspring.exceptions;

public class MultipleInjectConstructorException extends Exception {
    public MultipleInjectConstructorException() {
    }

    public MultipleInjectConstructorException(String message) {
        super(message);
    }
}
