package org.example;

import org.example.controller.ProductController;
import org.example.controller.SupplierController;
import org.example.model.CategoryDAO;
import org.example.model.ProductDAO;
import org.example.model.SupplierDAO;
import org.example.view.ProductView;
import org.example.view.SupplierView;

import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            try {
                // Initialize categories
                CategoryDAO categoryDAO = new CategoryDAO();
                categoryDAO.createTable();

                categoryDAO.addCategory("Grains");
                categoryDAO.addCategory("Fertilizer");
                categoryDAO.addCategory("Seeds");
                categoryDAO.addCategory("Equipment");

                // Initialize product category column
                ProductDAO productDAO = new ProductDAO();
                productDAO.ensureCategoryColumn();

                // Initialize suppliers table
                SupplierDAO supplierDAO = new SupplierDAO();
                supplierDAO.createTable();

                System.out.println(
                        "Database initialized successfully!"
                );

            } catch (SQLException e) {
                System.out.println(
                        "Database error: " + e.getMessage()
                );
                return;
            }

            // Open Product Management
            ProductDAO productDAO = new ProductDAO();
            ProductView productView = new ProductView();

            new ProductController(productDAO, productView);
            productView.showWindow();

            // Open Supplier Management
            SupplierDAO supplierDAO = new SupplierDAO();
            SupplierView supplierView = new SupplierView();

            new SupplierController(supplierDAO, supplierView);
            supplierView.showWindow();
        });
    }
}