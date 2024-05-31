package com.example.wooriga.factoryMethod;

public class MeetCertificateChargeFactory extends ChargeFactory {

    @Override
    protected int getPrice() {
        return 500;
    }

    @Override
    protected void chargePrice() {

    }
}
