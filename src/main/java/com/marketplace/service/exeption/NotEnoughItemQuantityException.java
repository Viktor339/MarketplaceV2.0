package com.marketplace.service.exeption;

public class NotEnoughItemQuantityException extends RuntimeException{
    public NotEnoughItemQuantityException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
