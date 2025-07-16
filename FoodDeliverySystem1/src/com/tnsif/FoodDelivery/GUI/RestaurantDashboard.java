package com.tnsif.FoodDelivery.GUI;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.tnsif.FoodDelivery.Models.Fooditems;
import com.tnsif.FoodDelivery.Models.Order;
import com.tnsif.FoodDelivery.Models.Restaurant;
import com.tnsif.FoodDelivery.Services.FoodServices;
import com.tnsif.FoodDelivery.Services.OrderServices;

public class RestaurantDashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    private Restaurant restaurant;
    private OrderServices orderService;
    private JTable ordersTable;
    private DefaultTableModel tableModel;

    public RestaurantDashboard(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.orderService = new OrderServices();
            setTitle("Restaurant Dashboard");
            setSize(400, 500);  // Matched LoginFrame size
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));  
            initComponents();
            loadOrders();
        }


    private void initComponents() {
        // Orders Table
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Customer ID", "Status", "Total Amount", "Order Date"}, 0);
        ordersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Buttons
        JButton refreshBtn = new JButton("Refresh Orders");
        refreshBtn.addActionListener(e -> loadOrders());

        JButton viewMenuBtn = new JButton("View Food Items");
        viewMenuBtn.addActionListener(e -> displayFoodItems());

        JButton addFoodBtn = new JButton("Add Food Item");
        addFoodBtn.addActionListener(e -> openAddFoodDialog());

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(refreshBtn);
        btnPanel.add(viewMenuBtn);
        btnPanel.add(addFoodBtn);

        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = orderService.getOrdersByRestaurant(restaurant.getUserId());
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                order.getOrderId(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getOrderDate()
            });
        }
    }

    private void displayFoodItems() {
        FoodServices foodServices = new FoodServices();
        List<Fooditems> foodList = foodServices.getFoodItemsByRestaurant(restaurant.getUserId());

        if (foodList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No food items found for your restaurant.");
            return;
        }

        String[] columns = {"ID", "Name", "Description", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Fooditems item : foodList) {
            model.addRow(new Object[]{
                item.getFoodId(),
                item.getName(),
                item.getDescription(),
                item.getPrice()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JDialog dialog = new JDialog(this, "Menu Items", true);
        dialog.setSize(600, 400);
        dialog.add(scrollPane);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openAddFoodDialog() {
        JTextField nameField = new JTextField(15);
        JTextField descField = new JTextField(15);
        JTextField priceField = new JTextField(10);
        JTextField categoryField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Price:")); panel.add(priceField);
        panel.add(new JLabel("Category:")); panel.add(categoryField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Food Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String desc = descField.getText();
            String priceText = priceField.getText();
            String category = categoryField.getText();

            if (name.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Price are required!");
                return;
            }

            try {
                double price = Double.parseDouble(priceText);

                Fooditems item = new Fooditems();
                item.setName(name);
                item.setDescription(desc);
                item.setPrice(price);
                item.setCategory(category);
                item.setRestaurantId(restaurant.getUserId());

                FoodServices fs = new FoodServices();
                boolean success = fs.addFoodItem(item);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Food item added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add food item.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price format.");
            }
        }
    }
}
