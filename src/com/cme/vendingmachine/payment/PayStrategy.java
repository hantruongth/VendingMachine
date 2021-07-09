package com.cme.vendingmachine.payment;

import com.cme.vendingmachine.model.Item;
import com.cme.vendingmachine.model.Payment;

import java.util.List;

/**
 * @author hantruong
 */
public interface PayStrategy {
    List<Payment> pay(Item item);

}
