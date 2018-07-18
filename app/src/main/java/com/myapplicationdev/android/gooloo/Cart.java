package com.myapplicationdev.android.gooloo;

public class Cart {
    String dish_name;
    int quantity;
    double price;

    public Cart(String dish_name, int quantity, Double price) {
        this.dish_name = dish_name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getDish_name() {
        return dish_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

}
