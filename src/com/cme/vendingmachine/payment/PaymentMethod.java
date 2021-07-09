package com.cme.vendingmachine.payment;

import com.cme.vendingmachine.exception.UnsupportedPaymentMethod;
import com.cme.vendingmachine.payment.impl.CashPayment;
import com.cme.vendingmachine.payment.impl.CoinPayment;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hantruong
 */
public enum PaymentMethod {
    COIN(1), CASH(2), CREDIT_CARD(3);

    public static Map<Integer, PaymentMethod> methodMap = Arrays.stream(values()).collect(Collectors.toMap(PaymentMethod::getValue, Function.identity()));

    private int value;

    PaymentMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PaymentMethod getMethod(int paymentMethod) {
        return methodMap.get(paymentMethod);
    }

    public static PayStrategy getPayment(int selectedMethod) {
        PaymentMethod method = methodMap.get(selectedMethod);
        switch (method) {
            case COIN:
                return new CoinPayment();
            case CASH:
                return new CashPayment();
            case CREDIT_CARD:
            default:
                throw new UnsupportedPaymentMethod("Unsupported Payment method, please select another one");

        }
    }
}
