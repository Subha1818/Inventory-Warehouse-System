
package org.example.controller;

import org.example.model.Product;
import org.example.model.ProductDAO;
import org.example.model.StockDAO;
import org.example.model.StockTransaction;
import org.example.view.StockView;

import java.sql.SQLException;
import java.util.List;

public class StockController {

    private final ProductDAO productDAO;
    private final StockDAO stockDAO;
    private final StockView view;

    public StockController(
            ProductDAO productDAO,
            StockDAO stockDAO,
            StockView view) {

        this.productDAO = productDAO;
        this.stockDAO = stockDAO;
        this.view = view;

        view.setRecordListener(e -> recordStock());
        view.setRefreshListener(e -> refreshData());

        refreshData();
    }

    private void recordStock() {
        Product product = view.getSelectedProduct();
        String type = view.getTransactionType();
        String quantityText = view.getQuantityText();

        if (product == null) {
            view.showMessage("Please add a product first.");
            return;
        }

        if (quantityText.isEmpty()) {
            view.showMessage("Please enter a quantity.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);

            if (quantity <= 0) {
                view.showMessage(
                        "Quantity must be greater than zero."
                );
                return;
            }

            stockDAO.recordStock(
                    product.getId(), type, quantity
            );

            view.showMessage("Stock transaction recorded!");
            view.clearQuantity();
            refreshData();

        } catch (NumberFormatException e) {
            view.showMessage(
                    "Please enter a valid whole number."
            );
        } catch (SQLException e) {
            view.showMessage(
                    "Stock error: " + e.getMessage()
            );
        }
    }

    private void refreshData() {
        try {
            List<Product> products = productDAO.getAllProducts();
            List<StockTransaction> transactions =
                    stockDAO.getAllTransactions();

            view.setProducts(products);
            view.displayTransactions(transactions);

        } catch (SQLException e) {
            view.showMessage(
                    "Unable to refresh: " + e.getMessage()
            );
        }
    }
}
