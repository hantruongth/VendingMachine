package com.cme.vendingmachine.impl;

import com.cme.vendingmachine.exception.NotEnoughAmountException;
import com.cme.vendingmachine.exception.NotSufficientChangeException;
import com.cme.vendingmachine.exception.OutOfOrderException;
import com.cme.vendingmachine.handler.ChangeHandler;
import com.cme.vendingmachine.handler.ChangeHandlerImpl;
import com.cme.vendingmachine.model.*;
import com.cme.vendingmachine.payment.PayStrategy;
import com.cme.vendingmachine.payment.PaymentMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author hantruong
 */
public class ConsoleVendingMachineImpl implements VendingMachine {

    private Inventory<Payment> coinCashInventory = new Inventory<>();
    //private Inventory<Cash> cashInventory = new Inventory<>();
    private Inventory<Item> itemInventory = new Inventory<>();
    private ChangeHandler changeHandler = new ChangeHandlerImpl();
    private Scanner scanner = new Scanner(System.in);

    private Item selectedItem;
    private long totalSales;
    private long currentBalance;

    public ConsoleVendingMachineImpl() {
        initInventory(10, 10, 10);
    }

    public void initInventory(int amountItems, int amountCoin, int amountCash) {
        Arrays.stream(Coin.values()).forEach(c -> coinCashInventory.put(c, amountCoin));
        Arrays.stream(Cash.values()).forEach(c -> coinCashInventory.put(c, amountCash));
        Arrays.stream(Item.values()).forEach(i -> itemInventory.put(i, amountItems));
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int selected = -1;
            greetingAndDisplayItems();
            while (selected == -1 || Item.getItem(selected) == null) {
                String selectedNumber = scanner.nextLine();
                try {
                    selected = Integer.parseInt(selectedNumber);
                    if (selected == 0) {
                        System.out.println("Bye Bye!");
                        return;
                    }
                    if (Item.getItem(selected) == null)
                        System.out.print(" [ERROR] Please select Item available above, select another number: ");
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Wrong number, please input the number for Item");
                }

            }
            try {
                Item selectedItem = Item.getItem(selected);
                selectItem(selectedItem);
                displaySelectedItemMessage();
                displayPaymentMethodMessage();

                int paymentMethod = 0;
                PaymentMethod methodEnum = null;
                while (paymentMethod == 0 || methodEnum == null) {
                    try {
                        String selectedPaymentMethod = scanner.nextLine();
                        paymentMethod = Integer.parseInt(selectedPaymentMethod);
                        methodEnum = PaymentMethod.getMethod(paymentMethod);
                        if (methodEnum == null) {
                            System.out.println(" [ERROR] Please select payment method above. Select again: ");
                        }
                    } catch (Exception e) {
                        System.out.println(" [ERROR] Please select payment method above. Select again: ");
                    }
                }

                PayStrategy payStrategy = PaymentMethod.getPayment(paymentMethod);
                List<Payment> payments = payStrategy.pay(selectedItem);

                updateInventory(payments, PaymentMethod.COIN);

                process(new VendingMachineRequest(selectedItem, currentBalance));

            } catch (Exception e) {
                System.out.println(" [ERROR] " + e.getMessage());
            }
        }
    }

    @Override
    public void greetingAndDisplayItems() {
        System.out.println(" *********************************************");
        System.out.println("     WELCOME TO THE VENDING MACHINE           ");
        System.out.println(" *********************************************");
        System.out.println("            Item available:               ");
        Arrays.stream(Item.values()).forEach(i -> {
            System.out.println(i.getSelectionNumber() + " - " + i.name() + " - Price: " + i.getPrice() + " cents");
        });
        System.out.println("0 - EXIT");
        System.out.println("");
        System.out.print(" Please select your item number: ");
    }

    @Override
    public void selectItem(Item item) throws OutOfOrderException {
        if (itemInventory.hasItem(item)) {
            selectedItem = item;
        } else
            throw new OutOfOrderException("Selected item is out of order, please select another item.");
    }

    @Override
    public void process(VendingMachineRequest request) throws NotSufficientChangeException, NotEnoughAmountException {
        collectItem();
        List<Payment> changes = calculateChanges(request);
        displayChangeMessage(changes);
    }

    private Item collectItem() throws NotSufficientChangeException, NotEnoughAmountException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                itemInventory.deduct(selectedItem);
                return selectedItem;
            }
            throw new NotSufficientChangeException("Not Sufficient change in Inventory");
        }
        throw new NotEnoughAmountException("Not fully paid, please insert more coins, remaining: " + (selectedItem.getPrice() - currentBalance));
    }

    private List<Payment> calculateChanges(VendingMachineRequest request) {
        List<Payment> changes = changeHandler.calculateChanges(request, coinCashInventory);
        updateCoinInventory(changes);
        currentBalance = 0;
        selectedItem = null;
        return changes;
    }

    private void updateCoinInventory(List<Payment> changes) {
        changes.stream().forEach(coin -> coinCashInventory.deduct(coin));
    }

    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(currentBalance - selectedItem.getPrice());
    }

    private boolean hasSufficientChangeForAmount(long amount) {
        boolean hasChange = true;
        try {
            changeHandler.calculateChanges(amount, coinCashInventory);
        } catch (NotSufficientChangeException exception) {
            hasChange = false;
        }
        return hasChange;
    }


    private boolean isFullPaid() {
        return currentBalance >= selectedItem.getPrice();
    }

    public void updateInventory(List<Payment> payments, PaymentMethod method) {
        payments.stream().forEach(p -> {
            coinCashInventory.add(p);
            currentBalance += p.getValue();
            totalSales += p.getValue();
        });
    }

    @Override
    public void displayChangeMessage(List<Payment> changes) {
        System.out.println(" ***Please collect the item and the changes");
        Optional<Integer> change = changes.stream().map(c -> c.getValue()).reduce(Integer::sum);
        double amount = change.isPresent() ? change.get() : 0;
        changes.stream().forEach(c-> System.out.println("  - " + c.getName() + " - " + c.getValue() + " cents"));

        System.out.println(" ***Total changes returned: " + (amount > 100 ? (amount / 100 + " USD" ) : amount + " cents") );
        System.out.println("=====================THANK YOU FOR YOUR PURCHASE=====================");
    }

    @Override
    public void displaySelectedItemMessage() {
        System.out.println(" You selected: " + selectedItem.name() + ", price: " + selectedItem.getPrice() + " cents");
    }

    @Override
    public void displayPaymentMethodMessage() {
        System.out.println(" Please select the payment method:");
        Arrays.stream(PaymentMethod.values()).forEach(p -> {
            System.out.println(p.getValue() + " - " + p.name());
        });
    }

    @Override
    public List<Payment> refund() {
        List<Payment> refund = changeHandler.calculateChanges(currentBalance, coinCashInventory);
        updateCoinInventory(refund);
        currentBalance = 0;
        selectedItem = null;
        return refund;
    }

    @Override
    public void reset() {
        coinCashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        selectedItem = null;
        currentBalance = 0;
    }

}
