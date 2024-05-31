package com.example.wooriga.factory;

import java.util.Arrays;
import java.util.List;

public class SpringContainer {

    private static final SpringContainer springContainer = new SpringContainer();
    private SpringContainer() {
    }

    public static SpringContainer getSpringContainer() {
        return springContainer;
    }

    public List<PizzaFactory> pizzaFactory() {
        return Arrays.asList(
            new MeatPizzaFactory(), new VegiPizzaFactory()
        );

    }
}
