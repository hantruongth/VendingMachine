package com.cme.vendingmachine.handler;

import com.cme.vendingmachine.model.Inventory;
import com.cme.vendingmachine.model.Payment;
import com.cme.vendingmachine.model.VendingMachineRequest;

import java.util.List;

/**
 * @author hantruong
 */
public class ChangeHandlerImpl implements ChangeHandler {

    private ChangeCalculator changeCalculator = new ChangeCalculatorImpl();

    @Override
    public List<Payment> calculateChanges(VendingMachineRequest request, Inventory<Payment> coinCashInventory) {
        long changeAmount = request.getCurrentBalance() - request.getItem().getPrice();
        return changeCalculator.calculateChange(coinCashInventory, changeAmount);
    }

    @Override
    public List<Payment> calculateChanges(long amount, Inventory<Payment> coinCashInventory) {
        return changeCalculator.calculateChange(coinCashInventory, amount);
    }
}
