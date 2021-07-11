package com.cme.vendingmachine.test;

import com.cme.vendingmachine.exception.NotSufficientChangeException;
import com.cme.vendingmachine.exception.OutOfOrderException;
import com.cme.vendingmachine.factory.VendingMachineFactory;
import com.cme.vendingmachine.handler.ChangeHandler;
import com.cme.vendingmachine.handler.ChangeHandlerImpl;
import com.cme.vendingmachine.impl.ConsoleVendingMachineImpl;
import com.cme.vendingmachine.impl.VendingMachine;
import com.cme.vendingmachine.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author hantruong
 */
public class VendingMachineTest {

    private static VendingMachine vendingMachine;
    private static Inventory<Payment> coinCashInventory = new Inventory<>();
    private static Inventory<Item> itemInventory = new Inventory<>();
    private static ChangeHandler changeHandler = new ChangeHandlerImpl();
    private static ConsoleVendingMachineImpl vm = new ConsoleVendingMachineImpl();

    @BeforeClass
    public static void setUp() {
        vendingMachine = VendingMachineFactory.getVendingMachine("console");
        Arrays.stream(Coin.values()).forEach(c -> coinCashInventory.put(c, 10));
        Arrays.stream(Cash.values()).forEach(c -> coinCashInventory.put(c, 10));
        Arrays.stream(Item.values()).forEach(i -> itemInventory.put(i, 10));
    }

    @AfterClass
    public static void tearDown() {
        vendingMachine = null;
        coinCashInventory.clear();
        itemInventory.clear();
    }

    @Test
    public void testCalculateChange1() {
        List<Payment> changes = changeHandler.calculateChanges(150, coinCashInventory);
        Map<Payment, Integer> mapCount = new HashMap<>();
        changes.forEach(c -> mapCount.merge(c, 1, Integer::sum));

        assertEquals(3, changes.size());
        assertEquals(1, mapCount.get(Cash.ONE_USD).intValue());
        assertEquals(2, mapCount.get(Coin.TWENTY_FIVE_CENTS).intValue());
    }

    @Test
    public void testCalculateChange2() {
        List<Payment> changes = changeHandler.calculateChanges(new VendingMachineRequest(Item.WATER, 100), coinCashInventory);
        Map<Payment, Integer> mapCount = new HashMap<>();
        changes.forEach(c -> mapCount.merge(c, 1, Integer::sum));

        assertEquals(4, changes.size());
        assertEquals(2, mapCount.get(Coin.TEN_CENTS).intValue());
        assertEquals(2, mapCount.get(Coin.TWENTY_FIVE_CENTS).intValue());
    }

    @Test(expected = NotSufficientChangeException.class)
    public void testCalculateChange2ThrowExceptionDueToNotSufficientChange() {
        changeHandler.calculateChanges(100000, coinCashInventory);

    }

    @Test(expected = OutOfOrderException.class)
    public void testResetWithOutOfOderException() {
        vendingMachine.reset();
        vendingMachine.selectItem(Item.ICE_CREAM);
    }

    @Test
    public void testRefund() {
        List<Payment> payments = Arrays.asList(Cash.TWO_USD);
        vm.updateInventory(payments);
        long totalRefund = vm.refund().stream().map(p -> p.getValue()).reduce(Integer::sum).get();
        assertEquals(200, totalRefund);
    }

    @Test(expected = OutOfOrderException.class)
    public void testSoldOutWithOutOfOrderException() {
        Item selectedItem = Item.PEPSI;
        for (int i = 0; i < 10; i++) {
            vendingMachine.selectItem(selectedItem);
            List<Payment> payments = Arrays.asList(Coin.TWENTY_FIVE_CENTS);
            vendingMachine.updateInventory(payments);
            long currentBalance = payments.stream().map(p -> p.getValue()).reduce(Integer::sum).get();
            vendingMachine.process(new VendingMachineRequest(selectedItem, currentBalance));
        }
    }

    @Test(expected = NotSufficientChangeException.class)
    public void testDontHaveEnoughChanges() {
        Item selectedItem = Item.PEPSI;
        for (int i = 0; i < 5; i++) {
            vendingMachine.selectItem(selectedItem);
            List<Payment> payments = Arrays.asList(Cash.TEN_USD);
            vendingMachine.updateInventory(payments);
            long currentBalance = payments.stream().map(p -> p.getValue()).reduce(Integer::sum).get();
            vendingMachine.process(new VendingMachineRequest(selectedItem, currentBalance));
        }
    }

}
