package com.example.wooriga.factory;

abstract class PizzaFactory {

    public abstract boolean isSupport(String type);

    public Pizza order() {
        Pizza pizza = createPizza();
        bake(pizza);
        wrap(pizza);
        return pizza;
    }

    private void bake(Pizza pizza) {
        System.out.println("bake " + pizza.getName());
    }

    private void wrap(Pizza pizza) {
        System.out.println("wrap up " + pizza.getName());
    }

    abstract protected Pizza createPizza();

}
