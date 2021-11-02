package com.marketplace.service.exeption;

public class UserOrderNotFoundException extends RuntimeException{
    public UserOrderNotFoundException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
