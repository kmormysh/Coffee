package com.panda.coffeeorderadmin.app_admin;

/**
 * Created by Katsiaryna on 2/17/2015.
 */
public class Order {
    private String name;
    private int orderStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    private Order(){ }

    public Order(String name, int orderStatus){

        this.name = name;
        this.orderStatus = orderStatus;
    }
}
