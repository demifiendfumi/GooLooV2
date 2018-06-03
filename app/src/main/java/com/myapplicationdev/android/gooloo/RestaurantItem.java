package com.myapplicationdev.android.gooloo;

public class RestaurantItem {

    private int id;
    private String imageName;
    private String resName;
    private double resRating;

    public RestaurantItem(int id, String imageName, String resName, double resRating) {
        this.id = id;
        this.imageName = imageName;
        this.resName = resName;
        this.resRating = resRating;
    }

    @Override
    public String toString() {
        return "RestaurantItem{" +
                "imageName='" + imageName + '\'' +
                ", resName='" + resName + '\'' +
                ", resRating=" + resRating +
                '}';
    }

    public int getId() { return id; }

    public String getImageName() {
        return imageName;
    }

    public String getResName() {
        return resName;
    }

    public double getResRating() {
        return resRating;
    }
}
