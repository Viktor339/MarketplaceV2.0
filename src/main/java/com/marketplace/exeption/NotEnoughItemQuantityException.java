package com.marketplace.exeption;

public class NotEnoughItemQuantityException extends RuntimeException{
    public NotEnoughItemQuantityException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
