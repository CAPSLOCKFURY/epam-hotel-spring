package com.example.epamhotelspring.exceptions.aop;

public class BindingResultNotFound extends RuntimeException {
    public BindingResultNotFound(String methodName) {
        super("Binding result not found on method ".concat(methodName));
    }
}
