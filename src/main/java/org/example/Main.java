
package org.example;

import org.example.controller.ProductController;
import org.example.controller.StockController;
import org.example.controller.SupplierController;

import org.example.model.CategoryDAO;
import org.example.model.ProductDAO;
import org.example.model.StockDAO;
import org.example.model.SupplierDAO;

import org.example.view.ProductView;
import org.example.view.StockView;
import org.example.view.SupplierView;

import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            try {
                CategoryDAO categoryDAO = new CategoryDAO();
                categoryDAO.createTable();

                categoryDAO.addCategory("Grains");
                categoryDAO.addCategory("Fertilizer");
                categoryDAO.addCategory("Seeds");
                categoryDAO.addCategory("Equipment");

                ProductDAO productDAO = new ProductDAO();
                productDAO.ensureCategoryColumn();

                SupplierDAO supplierDAO = new SupplierDAO();
                supplierDAO.createTable();

                StockDAO stockDAO = new StockDAO();
                stockDAO.createTable();

                System.out.println(
                        "Database initialized successfully!"
                );

            } catch (SQLException e) {
                System.out.println(
                        "Database error: " + e.getMessage()
                );
                return;
            }

            // Product window
            ProductDAO productDAO = new ProductDAO();
            ProductView productView = new ProductView();

            new ProductController(productDAO, productView);
            productView.showWindow();

            // Supplier window
            SupplierDAO supplierDAO = new SupplierDAO();
            SupplierView supplierView = new SupplierView();

            new SupplierController(supplierDAO, supplierView);
            supplierView.showWindow();

            // Stock window
            StockDAO stockDAO = new StockDAO();
            StockView stockView = new StockView();

            new StockController(
                    productDAO, stockDAO, stockView
            );
            stockView.showWindow();
        });
    }
}
