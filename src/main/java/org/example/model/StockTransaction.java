
package org.example.model;

public class StockTransaction {
    private int id;
    private int productId;
    private String productName;
    private String type;
    private int quantity;
    private String date;

    public StockTransaction(int id, int productId,
                            String productName, String type,
                            int quantity, String date) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}
