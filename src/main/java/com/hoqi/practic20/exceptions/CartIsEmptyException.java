package com.hoqi.practic20.exceptions;

public class CartIsEmptyException extends Exception {
    public CartIsEmptyException() {
        super();
    }

    public CartIsEmptyException(String message) {
        super(message);
    }

    public CartIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
