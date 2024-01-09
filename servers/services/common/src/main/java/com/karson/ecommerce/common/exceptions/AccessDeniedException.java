package com.karson.ecommerce.common.exceptions;

public class AccessDeniedException extends Exception {
    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
