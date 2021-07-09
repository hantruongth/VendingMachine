package com.cme.vendingmachine.exception;

/**
 * @author hantruong
 */
public class OutOfOrderException extends RuntimeException {
    private String message;

    public OutOfOrderException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
