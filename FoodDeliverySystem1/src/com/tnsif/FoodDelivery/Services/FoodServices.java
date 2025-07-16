package com.tnsif.FoodDelivery.Services;

import com.tnsif.FoodDelivery.Models.Cart;
import com.tnsif.FoodDelivery.Models.Fooditems;
import com.tnsif.FoodDelivery.Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodServices {

    // ✅ Add food item to cart
    public boolean addToCart(int customerId, int foodId, int quantity) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO cart (customer_id, food_id, quantity) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.setInt(2, foodId);
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ Clear cart for a customer
    public boolean clearCart(int customerId) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM cart WHERE customer_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ Get cart items for a customer
    public List<Cart> getCartItems(int customerId) {
        List<Cart> cartList = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        String query = "SELECT c.cart_id, c.food_id, c.quantity, f.name, f.description, f.price, f.restaurant_id " +
                       "FROM cart c " +
                       "JOIN food_item f ON c.food_id = f.id " +
                       "WHERE c.customer_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setFoodId(rs.getInt("food_id"));
                cart.setQuantity(rs.getInt("quantity"));

                Fooditems food = new Fooditems();
                food.setFoodId(rs.getInt("food_id"));
                food.setName(rs.getString("name"));
                food.setDescription(rs.getString("description"));
                food.setPrice(rs.getDouble("price"));
                food.setRestaurantId(rs.getInt("restaurant_id"));

                cart.setFoodItem(food);
                cartList.add(cart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;
    }

    // ✅ Get all food items
    public List<Fooditems> getAllFoodItems() {
        List<Fooditems> foodList = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        String query = "SELECT * FROM food_item";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Fooditems item = new Fooditems();
                item.setFoodId(rs.getInt("id"));
                item.setRestaurantId(rs.getInt("restaurant_id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setAvailable(true);
                foodList.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    // ✅ Get food items by restaurant
    public List<Fooditems> getFoodItemsByRestaurant(int restaurantId) {
        List<Fooditems> foodList = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        String query = "SELECT * FROM food_item WHERE restaurant_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Fooditems item = new Fooditems();
                item.setFoodId(rs.getInt("id"));
                item.setRestaurantId(rs.getInt("restaurant_id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setAvailable(true); 
                foodList.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    // ✅ Add new food item
    public boolean addFoodItem(Fooditems item) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO food_item (restaurant_id, name, description, price) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, item.getRestaurantId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDescription());
            stmt.setDouble(4, item.getPrice());

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
