package com.tnsif.FoodDelivery.Models;

public class Cart {
    private int cartId;
    private int customerId;
    private int foodId;
    private int quantity;
    private Fooditems foodItem;

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Fooditems getFoodItem() { return foodItem; }
    public void setFoodItem(Fooditems foodItem) { this.foodItem = foodItem; }
}
