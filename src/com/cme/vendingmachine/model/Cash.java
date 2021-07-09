package com.cme.vendingmachine.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Cash implements Payment{

    ONE_USD(100), TWO_USD(200), FIVE_USD(500), TEN_USD(1000);

    public static Map<Integer, Cash> cashMap = Arrays.stream(values()).collect(Collectors.toMap(Cash::getValue, Function.identity()));


    private int value;

    Cash(int value) {
        this.value = value;
    }

    public static Cash getCash(int value){
        return cashMap.get(value);
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
