package com.panda.katsiaryna.coffeeorder;

/**
 * Created by Katsiaryna on 2/17/2015.
 */
public class Order {
    private String name;
    private String coffeeType;

    public String getName() {
        return name;
    }

    public String getCoffeeType() {return coffeeType; };

    public void setName(String name) {
        this.name = name;
    }

    public void setCoffeeType(String coffeeType) {this.coffeeType = coffeeType; };

    private Order(){ }

    public Order(String name, String coffeeType){
        this.name = name;
        this.coffeeType = coffeeType;
    }
}
