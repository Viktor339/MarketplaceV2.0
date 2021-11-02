package com.marketplace.service.exeption;

public class UserOrderAlreadyExistException extends RuntimeException{
    public UserOrderAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
