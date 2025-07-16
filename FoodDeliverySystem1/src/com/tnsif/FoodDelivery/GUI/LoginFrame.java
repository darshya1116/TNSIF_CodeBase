package com.tnsif.FoodDelivery.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.tnsif.FoodDelivery.Models.Customer;
import com.tnsif.FoodDelivery.Models.Restaurant;
import com.tnsif.FoodDelivery.Services.CustomerServices;
import com.tnsif.FoodDelivery.Services.Restaurantservices;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeCombo;
    private CustomerServices customerService;
    private Restaurantservices restaurantService;
    private JButton loginButton;

    public LoginFrame() {
        this.customerService = new CustomerServices();
        this.restaurantService = new Restaurantservices();

        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setTitle("Food Delivery System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        userTypeCombo = new JComboBox<>(new String[]{"Customer", "Restaurant", "Delivery"});
        loginButton = new JButton("Login");
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(new JLabel("Food Delivery System", SwingConstants.CENTER), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("User Type:"), gbc);
        gbc.gridx = 1;
        add(userTypeCombo, gbc);

        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
    }

    private void setupEventHandlers() {
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userType.equals("Customer")) {
            Customer customer = customerService.authenticateCustomer(username, password);
            if (customer != null) {
                dispose();
                new CustomerDashboard(customer).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (userType.equals("Restaurant")) {
            Restaurant restaurant = restaurantService.authenticateRestaurant(username, password);
            if (restaurant != null) {
                dispose();
                new RestaurantDashboard(restaurant).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Delivery login not implemented yet", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleRegister() {
        String userType = (String) userTypeCombo.getSelectedItem();

        if (userType.equals("Customer")) {
            new CustomerRegistrationDialog(this).setVisible(true);
        } else if (userType.equals("Restaurant")) {
            new RestaurantRegistrationDialog(this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Delivery registration not implemented yet", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
