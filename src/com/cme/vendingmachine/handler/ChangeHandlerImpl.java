package com.cme.vendingmachine.handler;

import com.cme.vendingmachine.exception.NotSufficientChangeException;
import com.cme.vendingmachine.model.Inventory;
import com.cme.vendingmachine.model.Payment;
import com.cme.vendingmachine.model.VendingMachineRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hantruong
 */
public class ChangeHandlerImpl implements ChangeHandler {

    private ChangeCalculator changeCalculator = new ChangeCalculatorImpl();

    @Override
    public List<Payment> calculateChanges(VendingMachineRequest request, Inventory<Payment> coinCashInventory) throws NotSufficientChangeException {
        long changeAmount = request.getCurrentBalance() - request.getItem().getPrice();
        return calculateChanges(changeAmount, coinCashInventory);

    }

    @Override
    public List<Payment> calculateChanges(long amount, Inventory<Payment> coinCashInventory) throws NotSufficientChangeException {

        List<Payment> changes = changeCalculator.calculateChange(coinCashInventory, amount);
        if(!hasInventoryAvailable(changes, coinCashInventory))
            throw new NotSufficientChangeException("Not Sufficient Change Available in inventory");

        return changes;
    }

    private boolean hasInventoryAvailable(List<Payment> changes, Inventory<Payment> coinCashInventory) {

        Map<Payment, Integer> mapCount = new HashMap<>();
        changes.forEach(c->mapCount.merge(c, 1, Integer::sum));

        for(Payment c : mapCount.keySet()) {
            if(coinCashInventory.getQuantity(c) < mapCount.get(c)) {
                return false;
            }
        }
        return true;
    }
}
