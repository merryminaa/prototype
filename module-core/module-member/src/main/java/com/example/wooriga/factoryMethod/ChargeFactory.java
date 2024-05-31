package com.example.wooriga.factoryMethod;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public abstract class ChargeFactory {

    public void charge() {
        int price = getPrice();
        System.out.println("get price : " + price);

        boolean hasBalance = checkBalance(price);
        assert hasBalance;

        chargePrice();
        System.out.println("charge price");
    }

    abstract protected int getPrice();

    abstract protected void chargePrice();

    private boolean checkBalance(int price) {
        System.out.println("check balance");
        // ...
        return true;
    }

}
