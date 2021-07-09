package com.cme.vendingmachine.model;

/**
 * @author hantruong
 */
public class VendingMachineRequest {

    private Item item;
    private long currentBalance;

    public VendingMachineRequest(Item item, long currentBalance) {
        this.item = item;
        this.currentBalance = currentBalance;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance;
    }
}
