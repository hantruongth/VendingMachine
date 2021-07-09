package com.cme.vendingmachine.payment.impl;

import com.cme.vendingmachine.model.Coin;
import com.cme.vendingmachine.model.Item;
import com.cme.vendingmachine.model.Payment;
import com.cme.vendingmachine.payment.PayStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author hantruong
 */
public class CoinPayment implements PayStrategy {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public List<Payment> pay(Item selectedItem) {

        long currentBalance = 0;
        List<Payment> paidCoins = new ArrayList<>();

        while (currentBalance < selectedItem.getPrice()) {
            displayInsertCoin(selectedItem, currentBalance);
            String selectedNumber = scanner.nextLine();
            try {
                Coin selectedCoin = Coin.getCoin(Integer.parseInt(selectedNumber));
                if (selectedCoin == null) {
                    System.out.print(" [ERROR] Please select Coin available above");
                    continue;
                }
                paidCoins.add(selectedCoin);
                currentBalance += selectedCoin.getValue();
                System.out.println(" You inserted: " + selectedCoin.getValue() + " cents");
            } catch (NumberFormatException e) {
                System.out.println(" [ERROR - Please select the correct number]");
            }
        }

        return paidCoins;
    }

    private void displayInsertCoin(Item selectedItem, long currentBalance) {

        System.out.println(" PLEASE INSERT COINS, COIN ACCEPTED:");
        Arrays.stream(Coin.values()).forEach(i -> {
            System.out.println(i.getValue() + " - " + i.getName());
        });
        System.out.print(" Unpaid amount: " + (selectedItem.getPrice() - currentBalance) + " cents, Please select coin:");
    }
}
