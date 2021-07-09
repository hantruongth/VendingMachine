package com.cme.vendingmachine.payment.impl;

import com.cme.vendingmachine.model.Item;
import com.cme.vendingmachine.model.Payment;
import com.cme.vendingmachine.payment.PayStrategy;

import java.util.List;


/**
 * @author hantruong
 */
public class CreditCardPayment implements PayStrategy {

    @Override
    public List<Payment> pay(Item item) {
        return null;
    }
}
