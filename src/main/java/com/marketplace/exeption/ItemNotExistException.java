package com.marketplace.exeption;

public class ItemNotExistException extends RuntimeException{
    public ItemNotExistException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
