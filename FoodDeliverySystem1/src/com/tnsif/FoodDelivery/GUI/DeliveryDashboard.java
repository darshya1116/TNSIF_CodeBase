package com.tnsif.FoodDelivery.GUI;

import com.tnsif.FoodDelivery.Models.Order;
import com.tnsif.FoodDelivery.Models.DeliveryPerson;
import com.tnsif.FoodDelivery.Services.OrderServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DeliveryDashboard extends JFrame {
    private DeliveryPerson deliveryPerson;
    private OrderServices orderService;
    private JTable ordersTable;
    private DefaultTableModel tableModel;

    public DeliveryDashboard(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
        this.orderService = new OrderServices();
        setTitle("Delivery Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadOrders();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Customer ID", "Restaurant ID", "Status", "Total Amount", "Order Date"}, 0);
        ordersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadOrders());

        JButton updateStatusBtn = new JButton("Update Status");
        updateStatusBtn.addActionListener(e -> updateOrderStatus());

        JPanel btnPanel = new JPanel();
        btnPanel.add(refreshBtn);
        btnPanel.add(updateStatusBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = orderService.getOrdersByDeliveryPerson(deliveryPerson.getUserId());
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                order.getOrderId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getOrderDate()
            });
        }
    }

    private void updateOrderStatus() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an order to update status.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int orderId = (int) tableModel.getValueAt(selectedRow, 0);
        Order.OrderStatus[] statuses = Order.OrderStatus.values();
        Order.OrderStatus newStatus = (Order.OrderStatus) JOptionPane.showInputDialog(
            this,
            "Select new status:",
            "Update Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            statuses,
            statuses[0]
        );

        if (newStatus != null) {
            boolean updated = orderService.updateOrderStatus(orderId, newStatus);
            if (updated) {
                JOptionPane.showMessageDialog(this, "Order status updated.");
                loadOrders();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
