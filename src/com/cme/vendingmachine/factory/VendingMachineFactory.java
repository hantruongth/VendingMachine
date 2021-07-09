package com.cme.vendingmachine.factory;

import com.cme.vendingmachine.impl.VendingMachine;
import com.cme.vendingmachine.exception.InvalidVendingMachineTypeException;
import com.cme.vendingmachine.impl.ConsoleVendingMachineImpl;

/**
 * @author hantruong
 */
public class VendingMachineFactory {
    public static VendingMachine getVendingMachine(String type) {
        switch (type) {
            case "console":
                return new ConsoleVendingMachineImpl();
            default:
                throw new InvalidVendingMachineTypeException("Invalid Vending Machine Type");
        }
    }

}
