package com.myapplicationdev.android.gooloo;

public class DishesItem {
    private int dishId;
    private String dishImage;
    private String dishName;
    private String dishCNName;
    private String price;
    private String cuisine;

    public DishesItem(int dishId, String dishImage, String dishName, String dishCNName, String price, String cuisine) {
        this.dishId = dishId;
        this.dishImage = dishImage;
        this.dishName = dishName;
        this.dishCNName = dishCNName;
        this.price = price;
        this.cuisine = cuisine;
    }

    public int getDishId() {
        return dishId;
    }

    public String getDishImage() {
        return dishImage;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishCNName() {
        return dishCNName;
    }

    public String getPrice() {
        return price;
    }

    public String getCuisine() {
        return cuisine;
    }
}
