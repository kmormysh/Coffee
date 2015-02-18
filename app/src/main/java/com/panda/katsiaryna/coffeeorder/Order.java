package com.panda.katsiaryna.coffeeorder;

/**
 * Created by Katsiaryna on 2/17/2015.
 */
public class Order {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Order(){ }

    public Order(String name){
        this.name = name;
    }
}
