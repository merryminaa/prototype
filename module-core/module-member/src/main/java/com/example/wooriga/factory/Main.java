package com.example.wooriga.factory;

import java.util.List;

public class Main {

//    private final List<PizzaFactory> pizzaFactory;

    public static void main(String[] args) {
//        PizzaFactory pizzaFactory = SpringContainer.getSpringContainer().pizzaFactory();
        String type = "meat";
        List<PizzaFactory> pizzaFactories = SpringContainer.getSpringContainer().pizzaFactory();
        for (PizzaFactory pizzaFactory : pizzaFactories) {
            if (pizzaFactory.isSupport(type)) {
                Pizza pizza = pizzaFactory.order();
                System.out.println("product is " + pizza.getName());
            }
        }

    }

}
