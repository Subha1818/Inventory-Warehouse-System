
package InventorySystem.model;

import InventorySystem.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // Create the categories table
    public void createTable() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS categories (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE
                )
                """;

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute(sql);
        }
    }

    // Add a new category
    public boolean addCategory(String name) throws SQLException {
        String categoryName = name.trim();

        try (Connection con = Database.getConnection()) {
            String checkSql = "SELECT name FROM categories";

            try (PreparedStatement ps = con.prepareStatement(checkSql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (categoryName.equalsIgnoreCase(
                            rs.getString("name").trim())) {
                        return false;
                    }
                }
            }

            String insertSql =
                    "INSERT INTO categories (name) VALUES (?)";
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, categoryName);
                ps.executeUpdate();
            }
        }

        return true;
    }

    // Get all category names
    public List<String> getAllCategories() throws SQLException {

        List<String> categories = new ArrayList<>();

        String sql = "SELECT name FROM categories ORDER BY name";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("name"));
            }
        }

        return categories;
    }
}
