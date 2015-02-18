package com.panda.coffeeorderadmin.app_admin;

/**
 * Created by Katsiaryna on 2/17/2015.
 */
public class Order {
    private String id;
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

    public Order(String id, String name, int orderStatus){
        this.id = id;
        this.name = name;
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
