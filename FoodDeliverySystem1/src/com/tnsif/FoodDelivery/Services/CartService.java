package com.tnsif.FoodDelivery.Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tnsif.FoodDelivery.Models.CartItem;
import com.tnsif.FoodDelivery.Utils.DatabaseConnection;

public class CartService {

    public boolean addToCart(int customerId, int foodId, int quantity) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO cart (customer_id, food_id, quantity) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.setInt(2, foodId);
            stmt.setInt(3, quantity);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<CartItem> getCartItems(int customerId) {
        List<CartItem> cart = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT c.cart_id, f.name, f.price, c.quantity FROM cart c JOIN food_item f ON c.food_id = f.id WHERE c.customer_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartId(rs.getInt("cart_id"));
                item.setFoodName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                cart.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    public void clearCart(int customerId) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "DELETE FROM cart WHERE customer_id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
