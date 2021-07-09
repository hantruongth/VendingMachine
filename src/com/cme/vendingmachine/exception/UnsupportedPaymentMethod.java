package com.cme.vendingmachine.exception;

/**
 * @author hantruong
 */
public class UnsupportedPaymentMethod extends RuntimeException {
    private String message;

    public UnsupportedPaymentMethod(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}