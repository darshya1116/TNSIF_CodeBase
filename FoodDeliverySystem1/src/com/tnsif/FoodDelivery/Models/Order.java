package com.tnsif.FoodDelivery.Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private int restaurantId;
    private int deliveryId;
    private double totalAmount;
    private OrderStatus status;
    private String deliveryAddress;
    private Timestamp orderDate;
    private List<OrderItem> items;

    public enum OrderStatus {
        PENDING, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }

    public static class OrderItem {
        private int orderItemId;
        private int orderId;
        private int foodId;
        private int quantity;
        private double price;
        private Fooditems foodItem;

        public OrderItem() {}

        public OrderItem(int foodId, int quantity, double price) {
            this.foodId = foodId;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and Setters
        public int getOrderItemId() { return orderItemId; }
        public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

        public int getOrderId() { return orderId; }
        public void setOrderId(int orderId) { this.orderId = orderId; }

        public int getFoodId() { return foodId; }
        public void setFoodId(int foodId) { this.foodId = foodId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public Fooditems getFoodItem() { return foodItem; }
        public void setFoodItem(Fooditems foodItem) { this.foodItem = foodItem; }
    }

    public Order() {
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public Order(int customerId, int restaurantId, double totalAmount, String deliveryAddress) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public int getDeliveryId() { return deliveryId; }
    public void setDeliveryId(int deliveryId) { this.deliveryId = deliveryId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}