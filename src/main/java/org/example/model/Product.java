
package org.example.model;

public class Product {

    private int id;
    private String name;
    private int quantity;
    private String category;

    // Constructor for existing products
    public Product(int id, String name, int quantity) {
        this(id, name, quantity, "");
    }

    // Constructor with category
    public Product(int id, String name, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }
    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
