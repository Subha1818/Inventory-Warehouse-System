
package InventorySystem;

import InventorySystem.controller.ProductController;
import InventorySystem.controller.SupplierController;
import InventorySystem.controller.StockController;
import InventorySystem.controller.DashboardController;

import InventorySystem.model.CategoryDAO;
import InventorySystem.model.ProductDAO;
import InventorySystem.model.SupplierDAO;
import InventorySystem.model.StockDAO;

import InventorySystem.view.ProductView;
import InventorySystem.view.SupplierView;
import InventorySystem.view.StockView;
import InventorySystem.view.DashboardView;
import InventorySystem.view.UITheme;

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
                DashboardView dashboardView = new DashboardView();

                DashboardController dashboardController =
                        new DashboardController(
                                productDAO,
                                supplierDAO,
                                categoryDAO,
                                dashboardView
                        );
                Runnable refreshDashboard =
                        dashboardController::refreshStatistics;

                // Connect each panel to its controller
                new ProductController(
                        productDAO,
                        productView,
                        refreshDashboard
                );

                new SupplierController(
                        supplierDAO,
                        supplierView,
                        refreshDashboard
                );

                new StockController(
                        productDAO,
                        stockDAO,
                        stockView,
                        refreshDashboard
                );
                dashboardController.refreshStatistics();

                // Create the main application window
                JFrame frame = new JFrame(
                        "Inventory & Warehouse Management System"
                );

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 650);
                frame.setMinimumSize(new Dimension(800, 500));
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());
                frame.getContentPane().setBackground(UITheme.BACKGROUND);

                // Application heading
                JLabel heading = new JLabel(
                        "Inventory & Warehouse Management System",
                        SwingConstants.CENTER
                );

                heading.setFont(new Font("Arial", Font.BOLD, 20));
                heading.setForeground(UITheme.TEXT);
                heading.setBorder(
                        BorderFactory.createEmptyBorder(15, 10, 15, 10)
                );

                // Create tabs
                UIManager.put("TabbedPane.tabInsets", new Insets(8, 16, 8, 16));
                JTabbedPane tabs = new JTabbedPane();
                tabs.setBackground(UITheme.BACKGROUND);
                tabs.setForeground(UITheme.TEXT);
                tabs.setFont(new Font("Arial", Font.BOLD, 14));

                tabs.addTab("Products", productView);
                tabs.addTab("Suppliers", supplierView);
                tabs.addTab("Stock Management", stockView);
                for (int i = 0; i < tabs.getTabCount(); i++) {
                    tabs.setBackgroundAt(i, UITheme.LIGHT_TEAL);
                    tabs.setForegroundAt(i, UITheme.TEXT);
                }

                // Add the logo beside the heading
                JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
                header.setBackground(UITheme.BACKGROUND);
                header.add(new WarehouseLogo());
                header.add(heading);

                JPanel topPanel = new JPanel();
                topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
                topPanel.setBackground(UITheme.BACKGROUND);
                topPanel.add(header);
                topPanel.add(dashboardView);

                // Add heading, dashboard, and tabs to the main window
                frame.add(topPanel, BorderLayout.NORTH);
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

    private static class WarehouseLogo extends JLabel {

        WarehouseLogo() {
            setText("<html>Warehouse<br>System</html>");
            setForeground(Color.WHITE);
            setBackground(new Color(0, 128, 128));
            setFont(new Font("Arial", Font.BOLD, 9));
            setHorizontalAlignment(SwingConstants.CENTER);
            setPreferredSize(new Dimension(58, 58));
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        public boolean isOpaque() {
            return false;
        }
    }
}
