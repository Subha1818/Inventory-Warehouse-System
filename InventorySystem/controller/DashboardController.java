package InventorySystem.controller;

import InventorySystem.model.CategoryDAO;
import InventorySystem.model.ProductDAO;
import InventorySystem.model.SupplierDAO;
import InventorySystem.view.DashboardView;

import java.sql.SQLException;

public class DashboardController {

    private final ProductDAO productDAO;
    private final SupplierDAO supplierDAO;
    private final CategoryDAO categoryDAO;
    private final DashboardView view;

    public DashboardController(
            ProductDAO productDAO,
            SupplierDAO supplierDAO,
            CategoryDAO categoryDAO,
            DashboardView view) {
        this.productDAO = productDAO;
        this.supplierDAO = supplierDAO;
        this.categoryDAO = categoryDAO;
        this.view = view;
    }

    public void refreshStatistics() {
        try {
            view.displayStatistics(
                    productDAO.getTotalProducts(),
                    productDAO.getTotalStockUnits(),
                    supplierDAO.getTotalSuppliers(),
                    categoryDAO.getTotalCategories()
            );
        } catch (SQLException e) {
            view.showError("Unable to load dashboard statistics: "
                    + e.getMessage());
        }
    }
}
