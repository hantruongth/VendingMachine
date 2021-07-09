package com.cme.vendingmachine.exception;

/**
 * @author hantruong
 */
public class InvalidVendingMachineTypeException extends RuntimeException {
    private String message;

    public InvalidVendingMachineTypeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}