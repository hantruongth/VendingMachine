package com.cme.vendingmachine.impl;

import com.cme.vendingmachine.model.Item;
import com.cme.vendingmachine.model.Payment;
import com.cme.vendingmachine.model.VendingMachineRequest;

import java.util.List;
/**
 * @author hantruong
 */
public interface VendingMachine {

    void greetingAndDisplayItems();

    void selectItem(Item item);

    void start();

    void process(VendingMachineRequest request);

    void displayChangeMessage(List<Payment> changes);

    void displaySelectedItemMessage();

    void displayPaymentMethodMessage();

    List<Payment> refund();

    void reset();
}
