package com.hoqi.practic20.exceptions;

public class CartExistException extends Exception {
    public CartExistException() {
        super();
    }

    public CartExistException(String message) {
        super(message);
    }

    public CartExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
