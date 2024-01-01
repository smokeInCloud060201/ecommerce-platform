package com.karson.ecommerce.common.exceptions;

public class AcessDeniedException extends Exception {
    public AcessDeniedException(String message) {
        super(message);
    }

    public AcessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
