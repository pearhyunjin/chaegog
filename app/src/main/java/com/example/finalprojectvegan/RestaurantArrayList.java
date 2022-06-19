package com.example.finalprojectvegan;

public class RestaurantArrayList {
    public String name;
    public String address;
    public String image;

    public RestaurantArrayList() {}
    public RestaurantArrayList(String name, String address, String image){
        this.name = name;
        this.address = address;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
