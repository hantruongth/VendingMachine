package com.cme.vendingmachine.exception;

/**
 * @author hantruong
 */
public class NotEnoughAmountException extends RuntimeException {
    private String message;

    public NotEnoughAmountException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}