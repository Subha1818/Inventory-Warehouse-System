
package org.example.model;

import org.example.Database;

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
    public void addCategory(String name) throws SQLException {

        String sql = "INSERT OR IGNORE INTO categories (name) VALUES (?)";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ps.executeUpdate();
        }
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
