package com.hoqi.practic20.exceptions;

public class OrderRejectedException extends Exception {
    public OrderRejectedException() {
        super();
    }

    public OrderRejectedException(String message) {
        super(message);
    }

    public OrderRejectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
