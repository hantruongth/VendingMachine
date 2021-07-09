package com.cme.vendingmachine.payment.impl;

import com.cme.vendingmachine.model.Cash;
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
public class CashPayment implements PayStrategy {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public List<Payment> pay(Item selectedItem) {

        long currentBalance = 0;
        List<Payment> paidCashes = new ArrayList<>();

        while (currentBalance < selectedItem.getPrice()) {
            displayInsertCashMessage(selectedItem, currentBalance);
            String selectedNumber = scanner.nextLine();
            try {
                Cash selectedCash = Cash.getCash(Integer.parseInt(selectedNumber));
                if (selectedCash == null) {
                    System.out.print(" [ERROR] Please select CASH available above");
                    continue;
                }
                paidCashes.add(selectedCash);
                currentBalance += selectedCash.getValue();
                System.out.println(" You inserted: " + selectedCash.getValue()/100 + " USD");
            } catch (NumberFormatException e) {
                System.out.println(" [ERROR - Please select the correct number]");
            }
        }

        return paidCashes;
    }

    private void displayInsertCashMessage(Item selectedItem, long currentBalance) {

        System.out.println(" PLEASE INSERT CASH ACCEPTED:");
        Arrays.stream(Cash.values()).forEach(i -> {
            System.out.println(i.getValue() + " - " + i.getName());
        });
        System.out.print(" Unpaid amount: " + (selectedItem.getPrice() - currentBalance) + " cents, Please select cash:");
    }
}
