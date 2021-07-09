package com.cme.vendingmachine.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hantruong
 */
public enum Coin implements Payment{
    ONE_CENT(1), FIVE_CENTS(5), TEN_CENTS(10), TWENTY_FIVE_CENTS(25);

    public static Map<Integer, Coin> coinMap = Arrays.stream(values()).collect(Collectors.toMap(Coin::getValue, Function.identity()));

    private int value;

    Coin(int value) {
        this.value = value;
    }

    public static Coin getCoin(int coinInt) {
        return coinMap.get(coinInt);
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return name();
    }


}