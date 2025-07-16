package com.tnsif.FoodDelivery.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tnsif.FoodDelivery.Models.Restaurant;
import com.tnsif.FoodDelivery.Models.Users;
import com.tnsif.FoodDelivery.Utils.DatabaseConnection;

public class Restaurantservices {

    public boolean registerRestaurant(Restaurant restaurant) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Insert into users table
            String userQuery = "INSERT INTO users (username, email, password, phone, user_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, restaurant.getUsername());
            userStmt.setString(2, restaurant.getEmail());
            userStmt.setString(3, restaurant.getPassword());
            userStmt.setString(4, restaurant.getPhone());
            userStmt.setString(5, "RESTAURANT");

            int userResult = userStmt.executeUpdate();

            if (userResult > 0) {
                ResultSet keys = userStmt.getGeneratedKeys();
                if (keys.next()) {
                    int userId = keys.getInt(1);
                    restaurant.setUserId(userId);

                    // Insert into restaurants table
                    String restaurantQuery = "INSERT INTO restaurants (restaurant_id, name, address, cuisine_type) VALUES (?, ?, ?, ?)";
                    PreparedStatement restaurantStmt = conn.prepareStatement(restaurantQuery);
                    restaurantStmt.setInt(1, userId);
                    restaurantStmt.setString(2, restaurant.getName());
                    restaurantStmt.setString(3, restaurant.getAddress());
                    restaurantStmt.setString(4, restaurant.getCuisineType());

                    int restaurantResult = restaurantStmt.executeUpdate();

                    if (restaurantResult > 0) {
                        conn.commit();
                        return true;
                    }
                }
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Restaurant authenticateRestaurant(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT u.*, r.name, r.address, r.cuisine_type FROM users u JOIN restaurants r ON u.user_id = r.restaurant_id WHERE u.username = ? AND u.password = ? AND u.user_type = 'RESTAURANT'";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setUserId(rs.getInt("user_id"));
                restaurant.setUsername(rs.getString("username"));
                restaurant.setEmail(rs.getString("email"));
                restaurant.setPassword(rs.getString("password"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setCuisineType(rs.getString("cuisine_type"));
                restaurant.setUserType(Users.UserType.RESTAURANT);

                return restaurant;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}