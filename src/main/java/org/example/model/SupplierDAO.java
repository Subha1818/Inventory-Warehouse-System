package org.example.model;

import org.example.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS suppliers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    address TEXT
                )
                """;

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addSupplier(Supplier supplier) throws SQLException {
        String sql = """
                INSERT INTO suppliers (name, phone, address)
                VALUES (?, ?, ?)
                """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getPhone());
            ps.setString(3, supplier.getAddress());

            ps.executeUpdate();
        }
    }

    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();

        String sql = "SELECT * FROM suppliers ORDER BY id";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address")
                );

                suppliers.add(supplier);
            }
        }

        return suppliers;
    }
}