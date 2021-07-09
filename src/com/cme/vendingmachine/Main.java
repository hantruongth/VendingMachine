package com.cme.vendingmachine;

import com.cme.vendingmachine.factory.VendingMachineFactory;
import com.cme.vendingmachine.impl.VendingMachine;

/**
 * @author hantruong
 */
public class Main {

    public static void main(String[] args) {

        VendingMachine machine = VendingMachineFactory.getVendingMachine("console");
        machine.start();
    }
}
