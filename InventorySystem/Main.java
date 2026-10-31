
package InventorySystem;

import InventorySystem.controller.ProductController;
import InventorySystem.controller.SupplierController;
import InventorySystem.controller.StockController;

import InventorySystem.model.CategoryDAO;
import InventorySystem.model.ProductDAO;
import InventorySystem.model.SupplierDAO;
import InventorySystem.model.StockDAO;

import InventorySystem.view.ProductView;
import InventorySystem.view.SupplierView;
import InventorySystem.view.StockView;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                // Initialize database access
                CategoryDAO categoryDAO = new CategoryDAO();
                ProductDAO productDAO = new ProductDAO();
                SupplierDAO supplierDAO = new SupplierDAO();
                StockDAO stockDAO = new StockDAO();

                // Add default categories if they do not exist
                categoryDAO.addCategory("Grains");
                categoryDAO.addCategory("Fertilizer");
                categoryDAO.addCategory("Seeds");
                categoryDAO.addCategory("Equipment");

                // Create the three panels
                ProductView productView = new ProductView();
                SupplierView supplierView = new SupplierView();
                StockView stockView = new StockView();

                // Connect each panel to its controller
                new ProductController(productDAO, productView);

                new SupplierController(supplierDAO, supplierView);

                new StockController(
                        productDAO,
                        stockDAO,
                        stockView
                );

                // Create the main application window
                JFrame frame = new JFrame(
                        "Inventory & Warehouse Management System"
                );

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 650);
                frame.setMinimumSize(new Dimension(800, 500));
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());

                // Application heading
                JLabel heading = new JLabel(
                        "Inventory & Warehouse Management System",
                        SwingConstants.CENTER
                );

                heading.setFont(new Font("Arial", Font.BOLD, 20));
                heading.setBorder(
                        BorderFactory.createEmptyBorder(15, 10, 15, 10)
                );

                // Create tabs
                JTabbedPane tabs = new JTabbedPane();

                tabs.addTab("Products", productView);
                tabs.addTab("Suppliers", supplierView);
                tabs.addTab("Stock Management", stockView);

                // Add heading and tabs to the main window
                frame.add(heading, BorderLayout.NORTH);
                frame.add(tabs, BorderLayout.CENTER);

                // Display the application
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "Could not start the application:\n"
                                + e.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
