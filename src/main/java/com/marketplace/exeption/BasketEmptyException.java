package com.marketplace.exeption;

public class BasketEmptyException extends RuntimeException {
    public BasketEmptyException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
