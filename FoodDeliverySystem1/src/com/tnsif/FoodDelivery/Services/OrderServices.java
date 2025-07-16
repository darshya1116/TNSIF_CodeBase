package com.tnsif.FoodDelivery.Services;

import com.tnsif.FoodDelivery.Models.Order;
import com.tnsif.FoodDelivery.Models.Order.OrderItem;
import com.tnsif.FoodDelivery.Models.Cart;
import com.tnsif.FoodDelivery.Utils.DatabaseConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderServices {

    // Place a new order
    public boolean placeOrder(Order order, List<Cart> cartItems) {
        String sql = "INSERT INTO orders (customer_id, restaurant_id, delivery_id, status, total_amount, delivery_address, order_date) VALUES (?, ?, NULL, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setString(3, order.getStatus().name());
            stmt.setDouble(4, order.getTotalAmount());
            stmt.setString(5, order.getDeliveryAddress());
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) return false;

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    order.setOrderId(orderId);
                    // Insert order items
                    for (OrderItem item : order.getItems()) {
                        addOrderItem(orderId, item);
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addOrderItem(int orderId, OrderItem item) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, food_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, item.getFoodId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getPrice());
            stmt.executeUpdate();
        }
    }

    // Get orders by customer
    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(buildOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Get orders by restaurant
    public List<Order> getOrdersByRestaurant(int restaurantId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE restaurant_id = ? ORDER BY order_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(buildOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Get orders for delivery person
    public List<Order> getOrdersByDeliveryPerson(int deliveryId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE delivery_id = ? ORDER BY order_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deliveryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(buildOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Update order status
    public boolean updateOrderStatus(int orderId, Order.OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Assign delivery person to order
    public boolean assignDeliveryPerson(int orderId, int deliveryId) {
        String sql = "UPDATE orders SET delivery_id = ? WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deliveryId);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all orders (for dashboard)
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(buildOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Get orders by status
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ? ORDER BY order_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(buildOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Build an Order object from ResultSet
    private Order buildOrderFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setCustomerId(rs.getInt("customer_id"));
        order.setRestaurantId(rs.getInt("restaurant_id"));
        order.setDeliveryId(rs.getInt("delivery_id"));
        order.setStatus(Order.OrderStatus.valueOf(rs.getString("status")));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setDeliveryAddress(rs.getString("delivery_address"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setItems(getOrderItems(order.getOrderId()));
        return order;
    }

    private List<OrderItem> getOrderItems(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem(
                            rs.getInt("food_id"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    );
                    items.add(item);
                }
            }
        }
        return items;
    }
}