package com.marketplace.exeption;

public class UserOrderNotExistException extends RuntimeException{
    public UserOrderNotExistException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
