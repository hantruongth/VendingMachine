package com.cme.vendingmachine.handler;

import com.cme.vendingmachine.exception.NotSufficientChangeException;
import com.cme.vendingmachine.model.Cash;
import com.cme.vendingmachine.model.Coin;
import com.cme.vendingmachine.model.Inventory;
import com.cme.vendingmachine.model.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hantruong
 */
public class ChangeCalculatorImpl implements ChangeCalculator {
    @Override
    public List<Payment> calculateChange(Inventory<Payment> coinCashInventory, long amount) throws NotSufficientChangeException {
        List<Payment> changes = new ArrayList<>();

        if (amount > 0) {
            long balance = amount;
            while (balance > 0) {

                if (balance >= Cash.TEN_USD.getValue() && coinCashInventory.hasItem(Cash.TEN_USD)) {
                    changes.add(Cash.TEN_USD);
                    balance = balance - Cash.TEN_USD.getValue();
                    continue;

                } else if (balance >= Cash.FIVE_USD.getValue() && coinCashInventory.hasItem(Cash.FIVE_USD)) {
                    changes.add(Cash.FIVE_USD);
                    balance = balance - Cash.FIVE_USD.getValue();
                    continue;

                } else if (balance >= Cash.TWO_USD.getValue() && coinCashInventory.hasItem(Cash.TWO_USD)) {
                    changes.add(Cash.TWO_USD);
                    balance = balance - Cash.TWO_USD.getValue();
                    continue;

                } else if (balance >= Cash.ONE_USD.getValue() && coinCashInventory.hasItem(Cash.ONE_USD)) {
                    changes.add(Cash.ONE_USD);
                    balance = balance - Cash.ONE_USD.getValue();
                    continue;
                } else if (balance >= Coin.TWENTY_FIVE_CENTS.getValue() && coinCashInventory.hasItem(Coin.TWENTY_FIVE_CENTS)) {
                    changes.add(Coin.TWENTY_FIVE_CENTS);
                    balance = balance - Coin.TWENTY_FIVE_CENTS.getValue();
                    continue;

                } else if (balance >= Coin.TEN_CENTS.getValue() && coinCashInventory.hasItem(Coin.TEN_CENTS)) {
                    changes.add(Coin.TEN_CENTS);
                    balance = balance - Coin.TEN_CENTS.getValue();
                    continue;

                } else if (balance >= Coin.FIVE_CENTS.getValue() && coinCashInventory.hasItem(Coin.FIVE_CENTS)) {
                    changes.add(Coin.FIVE_CENTS);
                    balance = balance - Coin.FIVE_CENTS.getValue();
                    continue;

                } else if (balance >= Coin.ONE_CENT.getValue() && coinCashInventory.hasItem(Coin.ONE_CENT)) {
                    changes.add(Coin.ONE_CENT);
                    balance = balance - Coin.ONE_CENT.getValue();
                    continue;

                } else {
                    throw new NotSufficientChangeException("Machine not have sufficient changes to proceed, please select another item");
                }
            }
        }

        return changes;
    }
}
