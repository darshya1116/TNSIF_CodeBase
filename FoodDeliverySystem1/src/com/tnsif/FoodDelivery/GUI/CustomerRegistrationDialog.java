package com.tnsif.FoodDelivery.GUI;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.tnsif.FoodDelivery.Models.Customer;
import com.tnsif.FoodDelivery.Services.CustomerServices;

public class CustomerRegistrationDialog extends JDialog {
    private JTextField usernameField, emailField, passwordField, phoneField, addressField;
    private JButton registerButton, cancelButton;
    private boolean registered = false;

    public CustomerRegistrationDialog(Frame parent) {
        super(parent, "Customer Registration", true);
        initComponents();
    }

    private void initComponents() {
        // Main panel with reduced padding
        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); 

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        panel.add(usernameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField(10);
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        panel.add(passwordField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField(10);
        panel.add(phoneField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField(10);
        panel.add(addressField);

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        panel.add(registerButton);
        panel.add(cancelButton);

        registerButton.addActionListener(e -> registerCustomer());
        cancelButton.addActionListener(e -> dispose());

        setContentPane(panel);
        pack();
        setSize(390, 280); 
        setLocationRelativeTo(getParent());
    }

    private void registerCustomer() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = new Customer(username, email, password, phone, address);
        CustomerServices service = new CustomerServices();
        boolean success = service.registerCustomer(customer);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
            registered = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isRegistered() {
        return registered;
    }
}
