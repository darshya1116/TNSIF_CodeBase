package com.tnsif.FoodDelivery.GUI;

import com.tnsif.FoodDelivery.Models.Cart;
import com.tnsif.FoodDelivery.Models.Customer;
import com.tnsif.FoodDelivery.Models.Fooditems;
import com.tnsif.FoodDelivery.Models.Order;
import com.tnsif.FoodDelivery.Services.FoodServices;
import com.tnsif.FoodDelivery.Services.OrderServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerDashboard extends JFrame {
    private static final long serialVersionUID = 1L;

    private Customer customer;
    private FoodServices foodService;
    private OrderServices orderService;
    private JTable foodTable, cartTable, orderTable;
    private DefaultTableModel foodTableModel, cartTableModel, orderTableModel;
    private JLabel totalLabel;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        this.foodService = new FoodServices();
        this.orderService = new OrderServices();

        initializeComponents();
        setupLayout();
        loadData();
    }

    private void initializeComponents() {
        setTitle("Customer Dashboard - " + customer.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        String[] foodColumns = {"ID", "Name", "Description", "Price", "Category"};
        foodTableModel = new DefaultTableModel(foodColumns, 0);
        foodTable = new JTable(foodTableModel);

        String[] cartColumns = {"Cart ID", "Name", "Price", "Quantity", "Total"};
        cartTableModel = new DefaultTableModel(cartColumns, 0);
        cartTable = new JTable(cartTableModel);

        String[] orderColumns = {"Order ID", "Date", "Status", "Total"};
        orderTableModel = new DefaultTableModel(orderColumns, 0);
        orderTable = new JTable(orderTableModel);

        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Available Food Items"));

        JScrollPane foodScrollPane = new JScrollPane(foodTable);
        foodScrollPane.setPreferredSize(new Dimension(0, 200));
        topPanel.add(foodScrollPane, BorderLayout.CENTER);

        JPanel foodButtonPanel = new JPanel();
        JButton addToCartButton = new JButton("Add to Cart");
        JButton refreshFoodButton = new JButton("Refresh");
        foodButtonPanel.add(addToCartButton);
        foodButtonPanel.add(refreshFoodButton);
        topPanel.add(foodButtonPanel, BorderLayout.SOUTH);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setPreferredSize(new Dimension(0, 150));
        middlePanel.add(cartScrollPane, BorderLayout.CENTER);

        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton placeOrderButton = new JButton("Place Order");
        JButton clearCartButton = new JButton("Clear Cart");
        cartButtonPanel.add(totalLabel);
        cartButtonPanel.add(placeOrderButton);
        cartButtonPanel.add(clearCartButton);
        middlePanel.add(cartButtonPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Order History"));

        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        orderScrollPane.setPreferredSize(new Dimension(0, 200));
        bottomPanel.add(orderScrollPane, BorderLayout.CENTER);

        JPanel orderButtonPanel = new JPanel();
        JButton refreshOrdersButton = new JButton("Refresh Orders");
        JButton viewOrderDetailsButton = new JButton("View Details");
        orderButtonPanel.add(refreshOrdersButton);
        orderButtonPanel.add(viewOrderDetailsButton);
        bottomPanel.add(orderButtonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addToCartButton.addActionListener(e -> addToCart());
        refreshFoodButton.addActionListener(e -> loadFoodItems());
        placeOrderButton.addActionListener(e -> placeOrder());
        clearCartButton.addActionListener(e -> clearCart());
        refreshOrdersButton.addActionListener(e -> loadOrders());
        viewOrderDetailsButton.addActionListener(e -> viewOrderDetails());
    }

    private void loadData() {
        loadFoodItems();
        loadCartItems();
        loadOrders();
    }

    private void loadFoodItems() {
        foodTableModel.setRowCount(0);
        List<Fooditems> foodItems = foodService.getAllFoodItems();
        for (Fooditems item : foodItems) {
            Object[] row = {
                item.getFoodId(),
                item.getName(),
                item.getDescription(),
                String.format("₹%.2f", item.getPrice()),
                item.getCategory()
            };
            foodTableModel.addRow(row);
        }
    }

    private void loadCartItems() {
        cartTableModel.setRowCount(0);
        List<Cart> cartItems = foodService.getCartItems(customer.getUserId());

        double total = 0;
        for (Cart item : cartItems) {
            double itemTotal = item.getFoodItem().getPrice() * item.getQuantity();
            total += itemTotal;
            Object[] row = {
                item.getCartId(),
                item.getFoodItem().getName(),
                String.format("₹%.2f", item.getFoodItem().getPrice()),
                item.getQuantity(),
                String.format("₹%.2f", itemTotal)
            };
            cartTableModel.addRow(row);
        }
        totalLabel.setText(String.format("Total: ₹%.2f", total));
    }

    private void loadOrders() {
        orderTableModel.setRowCount(0);
        List<Order> orders = orderService.getOrdersByCustomer(customer.getUserId());
        for (Order order : orders) {
            Object[] row = {
                order.getOrderId(),
                order.getOrderDate(),
                order.getStatus(),
                String.format("₹%.2f", order.getTotalAmount())
            };
            orderTableModel.addRow(row);
        }
    }

    private void addToCart() {
        int selectedRow = foodTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a food item.");
            return;
        }

        int foodId = (int) foodTableModel.getValueAt(selectedRow, 0);
        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:");

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be positive.");
                return;
            }

            boolean added = foodService.addToCart(customer.getUserId(), foodId, quantity);
            if (added) {
                JOptionPane.showMessageDialog(this, "Item added to cart!");
                loadCartItems();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add item to cart.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format.");
        }
    }

    private void clearCart() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the cart?",
                "Confirm Clear", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean cleared = foodService.clearCart(customer.getUserId());
            if (cleared) {
                JOptionPane.showMessageDialog(this, "Cart cleared!");
                loadCartItems();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to clear cart.");
            }
        }
    }

    private void placeOrder() {
        List<Cart> cartItems = foodService.getCartItems(customer.getUserId());
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        String address = JOptionPane.showInputDialog(this, "Enter delivery address:", customer.getAddress());
        if (address == null || address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address cannot be empty.");
            return;
        }

        double total = 0;
        int restaurantId = cartItems.get(0).getFoodItem().getRestaurantId();
        for (Cart item : cartItems) {
            total += item.getFoodItem().getPrice() * item.getQuantity();
        }

        Order order = new Order(customer.getUserId(), restaurantId, total, address);
        boolean success = orderService.placeOrder(order, cartItems);
        if (success) {
            JOptionPane.showMessageDialog(this, "Order placed successfully!");
            loadCartItems();
            loadOrders();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to place order.");
        }
    }

    private void viewOrderDetails() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order.");
            return;
        }

        int orderId = (int) orderTableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this, "Order Details (ID: " + orderId + ") – [Feature Coming Soon]");
    }
}
