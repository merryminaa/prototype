package com.example.wooriga.factory;

import org.springframework.stereotype.Component;


@Component
public class MeatPizzaFactory extends PizzaFactory {

    @Override
    public boolean isSupport(String type) {
        return type.equals("meat");
    }

    @Override
    protected Pizza createPizza() {
        System.out.println("meat pizza cooking");
        return new MeatPizza();
    }
}
