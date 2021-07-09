package com.cme.vendingmachine.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hantruong
 */
public enum Item {

    ICE_CREAM(1, 20), COKE(2,50), PEPSI(3, 25), WATER(4,30), SANDWICH(5,150);

    public static Map<Integer, Item> itemMap = Arrays.stream(values()).collect(Collectors.toMap(Item::getSelectionNumber, Function.identity()));

    private int selectionNumber;
    private int price;

    Item(int selectionNumber, int price){
        this.selectionNumber = selectionNumber;
        this.price = price;
    }

    public int getSelectionNumber(){
        return selectionNumber;
    }

    public int getPrice(){
        return this.price;
    }

    public static Item getItem(int itemInt) {
        return itemMap.get(itemInt);
    }


}
