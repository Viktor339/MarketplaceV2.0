package com.marketplace.exeption;

public class UserOrderAlreadyExistException extends RuntimeException{
    public UserOrderAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
