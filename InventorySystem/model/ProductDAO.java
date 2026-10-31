
package InventorySystem.model;

import InventorySystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Add a product with its category
    public void addProduct(Product product) throws SQLException {

        String sql = """
                INSERT INTO products (name, quantity, category_id)
                VALUES (?, ?, (SELECT id FROM categories WHERE name = ?))
                """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity());
            ps.setString(3, product.getCategory());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Product could not be added.");
            }
        }
    }

    // Retrieve products along with their category names
    public List<Product> getAllProducts() throws SQLException {

        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT p.id, p.name, p.quantity,
                       COALESCE(c.name, 'Uncategorized') AS category
                FROM products p
                LEFT JOIN categories c ON p.category_id = c.id
                ORDER BY p.id
                """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );

                products.add(product);
            }
        }

        return products;
    }

    // Add the category_id column if it does not exist
    public void ensureCategoryColumn() throws SQLException {

        boolean columnExists = false;

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "PRAGMA table_info(products)")) {

            while (rs.next()) {
                if ("category_id".equals(rs.getString("name"))) {
                    columnExists = true;
                    break;
                }
            }

            if (!columnExists) {
                stmt.executeUpdate(
                        "ALTER TABLE products ADD COLUMN category_id INTEGER"
                );
            }
        }
    }
}
