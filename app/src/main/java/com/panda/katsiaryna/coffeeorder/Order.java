package com.panda.katsiaryna.coffeeorder;

/**
 * Created by Katsiaryna on 2/17/2015.
 */
public class Order {
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

    private String name;
    private int orderStatus;

    private Order(){
    }

    public Order(String name, int orderStatus){

        this.name = name;
        this.orderStatus = orderStatus;
    }
}
