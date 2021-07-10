package test;

import com.cme.vendingmachine.exception.NotSufficientChangeException;
import com.cme.vendingmachine.factory.VendingMachineFactory;
import com.cme.vendingmachine.handler.ChangeCalculator;
import com.cme.vendingmachine.handler.ChangeCalculatorImpl;
import com.cme.vendingmachine.handler.ChangeHandler;
import com.cme.vendingmachine.handler.ChangeHandlerImpl;
import com.cme.vendingmachine.impl.VendingMachine;
import com.cme.vendingmachine.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hantruong
 */
public class VendingMachineTest {

    private static VendingMachine vendingMachine;
    private static Inventory<Payment> coinCashInventory = new Inventory<>();
    private static Inventory<Item> itemInventory = new Inventory<>();
    private static ChangeHandler changeHandler = new ChangeHandlerImpl();
    private static ChangeCalculator changeCalculator = new ChangeCalculatorImpl();

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
        changes.forEach(c->mapCount.merge(c, 1, Integer::sum));

        assertEquals(3, changes.size());
        assertEquals(1, mapCount.get(Cash.ONE_USD).intValue());
        assertEquals(2, mapCount.get(Coin.TWENTY_FIVE_CENTS).intValue());
    }

    @Test
    public void testCalculateChange2() {
        List<Payment> changes = changeHandler.calculateChanges(new VendingMachineRequest(Item.WATER, 100), coinCashInventory);
        Map<Payment, Integer> mapCount = new HashMap<>();
        changes.forEach(c->mapCount.merge(c, 1, Integer::sum));

        assertEquals(4, changes.size());
        assertEquals(2, mapCount.get(Coin.TEN_CENTS).intValue());
        assertEquals(2, mapCount.get(Coin.TWENTY_FIVE_CENTS).intValue());
    }

    @Test(expected = NotSufficientChangeException.class)
    public void testCalculateChange2ThrowExceptionDueToNotSufficientChange() {
        changeHandler.calculateChanges(100000, coinCashInventory);

    }

}
