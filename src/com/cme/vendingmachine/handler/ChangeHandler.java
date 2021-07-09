package com.cme.vendingmachine.handler;

import com.cme.vendingmachine.model.Coin;
import com.cme.vendingmachine.model.Inventory;
import com.cme.vendingmachine.model.Payment;
import com.cme.vendingmachine.model.VendingMachineRequest;

import java.util.List;

/**
 * @author hantruong
 */
public interface ChangeHandler {

    List<Payment> calculateChanges(VendingMachineRequest request, Inventory<Payment> coinCashInventory);
    List<Payment> calculateChanges(long amount, Inventory<Payment> coinCashInventory);
}
