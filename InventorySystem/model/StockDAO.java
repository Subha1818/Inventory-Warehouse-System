
package InventorySystem.model;

import InventorySystem.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    public void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS stock_transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    product_id INTEGER NOT NULL,
                    type TEXT NOT NULL CHECK(type IN ('IN', 'OUT')),
                    quantity INTEGER NOT NULL CHECK(quantity > 0),
                    transaction_date TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (product_id) REFERENCES products(id)
                )
                """;

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void recordStock(int productId, String type, int quantity)
            throws SQLException {

        if (!type.equals("IN") && !type.equals("OUT")) {
            throw new IllegalArgumentException(
                    "Type must be IN or OUT."
            );
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);

            try {
                // Check that the product exists.
                int currentQuantity;

                String checkSql =
                        "SELECT quantity FROM products WHERE id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(checkSql)) {
                    ps.setInt(1, productId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Product not found."
                            );
                        }

                        currentQuantity = rs.getInt("quantity");
                    }
                }

                // Update the product quantity.
                String updateSql;

                if (type.equals("IN")) {
                    updateSql = """
                            UPDATE products
                            SET quantity = quantity + ?
                            WHERE id = ?
                            """;
                } else {
                    updateSql = """
                            UPDATE products
                            SET quantity = quantity - ?
                            WHERE id = ? AND quantity >= ?
                            """;
                }

                try (PreparedStatement ps =
                             con.prepareStatement(updateSql)) {
                    ps.setInt(1, quantity);
                    ps.setInt(2, productId);

                    if (type.equals("OUT")) {
                        ps.setInt(3, quantity);
                    }

                    int rows = ps.executeUpdate();

                    if (rows == 0) {
                        throw new SQLException(
                                "Insufficient stock available."
                        );
                    }
                }

                // Save the stock movement in history.
                String insertSql = """
                        INSERT INTO stock_transactions
                            (product_id, type, quantity)
                        VALUES (?, ?, ?)
                        """;

                try (PreparedStatement ps =
                             con.prepareStatement(insertSql)) {
                    ps.setInt(1, productId);
                    ps.setString(2, type);
                    ps.setInt(3, quantity);
                    ps.executeUpdate();
                }

                con.commit();

            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public List<StockTransaction> getAllTransactions()
            throws SQLException {

        List<StockTransaction> transactions = new ArrayList<>();

        String sql = """
                SELECT st.id, st.product_id, p.name AS product_name,
                       st.type, st.quantity,
                       st.transaction_date
                FROM stock_transactions st
                JOIN products p ON st.product_id = p.id
                ORDER BY st.id DESC
                """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StockTransaction transaction = new StockTransaction(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("type"),
                        rs.getInt("quantity"),
                        rs.getString("transaction_date")
                );

                transactions.add(transaction);
            }
        }

        return transactions;
    }
}
