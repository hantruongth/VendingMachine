package com.cme.vendingmachine.handler;

import com.cme.vendingmachine.model.Inventory;
import com.cme.vendingmachine.model.Payment;

import java.util.List;

public interface ChangeCalculator {
    List<Payment> calculateChange(Inventory<Payment> coinInventory, long amount);
}
