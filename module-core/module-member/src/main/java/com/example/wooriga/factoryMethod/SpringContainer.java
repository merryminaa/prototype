package com.example.wooriga.factoryMethod;

public class SpringContainer {

    private static final SpringContainer springContainer = new SpringContainer();

    public SpringContainer() {
    }

    public SpringContainer getSpringContainer() {
        return springContainer;
    }

    public ChargeFactory chargeFactory() {
        return new MeetChargeFactory();
    }
}
