package com.marketplace.exeption;

public class ProductAlreadyExistException extends RuntimeException{
    public ProductAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
