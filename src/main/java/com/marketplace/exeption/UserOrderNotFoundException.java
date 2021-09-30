package com.marketplace.exeption;

public class UserOrderNotFoundException extends RuntimeException{
    public UserOrderNotFoundException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
