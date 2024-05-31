package com.example.wooriga.factory;

import org.springframework.stereotype.Component;

@Component
public class VegiPizzaFactory extends PizzaFactory {

    @Override
    public boolean isSupport(String type) {
        return type.equals("vegi");
    }

    @Override
    protected Pizza createPizza() {
        System.out.println("vegi pizza cooking");
        return new VegiPizza();
    }
}
