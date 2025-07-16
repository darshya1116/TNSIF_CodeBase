package com.tnsif.FoodDelivery.Models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant extends Users {
    private String name;
    private String address;
    private String cuisineType;
    private List<Fooditems> menu;

    public Restaurant() {
        super();
        this.menu = new ArrayList<>();
    }

    public Restaurant(String username, String email, String password, String phone,
                      String name, String address, String cuisineType) {
        super(username, email, password, phone, UserType.RESTAURANT);
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.menu = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCuisineType() { return cuisineType; }
    public void setCuisineType(String cuisineType) { this.cuisineType = cuisineType; }

    public List<Fooditems> getMenu() { return menu; }
    public void setMenu(List<Fooditems> menu) { this.menu = menu; }
}