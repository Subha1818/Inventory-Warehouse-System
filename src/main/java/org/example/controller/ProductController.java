
package org.example.controller;

import org.example.model.CategoryDAO;
import org.example.model.Product;
import org.example.model.ProductDAO;
import org.example.view.ProductView;

import java.sql.SQLException;
import java.util.List;

public class ProductController {

    private ProductDAO dao;
    private CategoryDAO categoryDAO;
    private ProductView view;

    public ProductController(ProductDAO dao, ProductView view) {

        this.dao = dao;
        this.categoryDAO = new CategoryDAO();
        this.view = view;

        // Connect buttons to controller methods
        view.setAddListener(e -> addProduct());
        view.setRefreshListener(e -> loadProducts());

        // Load data when the application starts
        loadCategories();
        loadProducts();
    }

    // Load categories into the dropdown
    private void loadCategories() {
        try {
            List<String> categories = categoryDAO.getAllCategories();
            view.setCategories(categories);

        } catch (SQLException e) {
            view.showMessage(
                    "Unable to load categories: " + e.getMessage()
            );
        }
    }

    // Add a new product
    private void addProduct() {

        String name = view.getProductName();
        String quantityText = view.getQuantity();
        String category = view.getSelectedCategory();

        if (name.isEmpty() || quantityText.isEmpty()) {
            view.showMessage("Please enter product name and quantity.");
            return;
        }

        if (category.isEmpty()) {
            view.showMessage("Please select a category.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);

            if (quantity < 0) {
                view.showMessage("Quantity cannot be negative.");
                return;
            }

            Product product = new Product(
                    0, name, quantity, category
            );

            dao.addProduct(product);

            view.showMessage("Product added successfully!");
            view.clearFields();

            loadProducts();

        } catch (NumberFormatException e) {
            view.showMessage("Quantity must be a valid whole number.");

        } catch (SQLException e) {
            view.showMessage(
                    "Database error: " + e.getMessage()
            );
        }
    }

    // Load products into the table
    private void loadProducts() {
        try {
            List<Product> products = dao.getAllProducts();
            view.displayProducts(products);

        } catch (SQLException e) {
            view.showMessage(
                    "Unable to load products: " + e.getMessage()
            );
        }
    }
}
