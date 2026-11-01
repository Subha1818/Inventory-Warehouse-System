package InventorySystem.controller;

import InventorySystem.model.Supplier;
import InventorySystem.model.SupplierDAO;
import InventorySystem.view.SupplierView;

import java.sql.SQLException;
import java.util.List;

public class SupplierController {

    private final SupplierDAO dao;
    private final SupplierView view;

    public SupplierController(SupplierDAO dao, SupplierView view) {
        this.dao = dao;
        this.view = view;

        view.setAddListener(e -> addSupplier());
        view.setRefreshListener(e -> loadSuppliers());

        loadSuppliers();
    }

    private void addSupplier() {
        String name = view.getSupplierName();
        String phone = view.getPhone();
        String address = view.getAddress();

        if (name.isEmpty()) {
            view.showWarning("Supplier name cannot be empty.");
            return;
        }

        if (phone.isEmpty()) {
            view.showWarning("Phone number cannot be empty.");
            return;
        }

        if (!phone.matches("[0-9]{10}")) {
            view.showWarning(
                    "Phone number must contain exactly 10 digits (0-9)."
            );
            return;
        }

        try {
            Supplier supplier =
                    new Supplier(0, name, phone, address);

            dao.addSupplier(supplier);

            view.showMessage("Supplier added successfully!");
            view.clearFields();

            loadSuppliers();

        } catch (SQLException e) {
            view.showMessage(
                    "Database error: " + e.getMessage()
            );
        }
    }

    private void loadSuppliers() {
        try {
            List<Supplier> suppliers = dao.getAllSuppliers();
            view.displaySuppliers(suppliers);

        } catch (SQLException e) {
            view.showMessage(
                    "Unable to load suppliers: " + e.getMessage()
            );
        }
    }
}