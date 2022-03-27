package com.example.epamhotelspring.exceptions.aop;

public class RedirectAttributesNotFound extends RuntimeException {
    public RedirectAttributesNotFound(String methodName) {
        super("Redirect attributes not found on method ".concat(methodName));
    }
}
