package com.example.wooriga.factoryMethod;

public class MeetChargeFactory extends ChargeFactory {

    @Override
    protected int getPrice() {
        return 1000;
    }

    @Override
    protected void chargePrice() {

    }
}
