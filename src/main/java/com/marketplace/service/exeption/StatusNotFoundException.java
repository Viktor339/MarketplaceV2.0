package com.marketplace.service.exeption;

public class StatusNotFoundException extends RuntimeException{
    public StatusNotFoundException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
