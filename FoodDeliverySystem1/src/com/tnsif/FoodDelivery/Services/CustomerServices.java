package com.tnsif.FoodDelivery.Services;

import com.tnsif.FoodDelivery.Models.Customer;
import com.tnsif.FoodDelivery.Models.Users;
import com.tnsif.FoodDelivery.Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServices {

    public boolean registerCustomer(Customer customer) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO customer (username, email, password, phone, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPassword());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer authenticateCustomer(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM customer WHERE username = ? AND password = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setUserId(rs.getInt("id")); // Assuming your table uses 'id' as PK
                customer.setUsername(rs.getString("username"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setUserType(Users.UserType.CUSTOMER);

                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT * FROM customer";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setUserId(rs.getInt("id"));
                customer.setUsername(rs.getString("username"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setUserType(Users.UserType.CUSTOMER);

                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
