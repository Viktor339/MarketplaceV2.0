package com.marketplace.exeption;

public class ItemAlreadyInTheUserBasketException extends RuntimeException{
    public ItemAlreadyInTheUserBasketException(String message) {
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
