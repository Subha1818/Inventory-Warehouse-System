
package org.example.view;

import org.example.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductView extends JPanel {

    private JTextField nameField;
    private JTextField quantityField;
    private JComboBox<String> categoryComboBox;
    private DefaultTableModel tableModel;

    private ActionListener addListener;
    private ActionListener refreshListener;

    public ProductView() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input fields
        JPanel inputPanel = new JPanel(new FlowLayout());

        nameField = new JTextField(10);
        quantityField = new JTextField(5);
        categoryComboBox = new JComboBox<>();

        JButton addButton = new JButton("Add Product");
        JButton refreshButton = new JButton("Refresh");

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryComboBox);

        inputPanel.add(addButton);
        inputPanel.add(refreshButton);

        // Product table
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Product Name", "Quantity", "Category"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel layout
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(e -> {
            if (addListener != null) {
                addListener.actionPerformed(e);
            }
        });

        refreshButton.addActionListener(e -> {
            if (refreshListener != null) {
                refreshListener.actionPerformed(e);
            }
        });
    }

    public void setAddListener(ActionListener listener) {
        addListener = listener;
    }

    public void setRefreshListener(ActionListener listener) {
        refreshListener = listener;
    }

    public String getProductName() {
        return nameField.getText().trim();
    }

    public String getQuantity() {
        return quantityField.getText().trim();
    }

    public String getSelectedCategory() {
        Object selected = categoryComboBox.getSelectedItem();
        return selected == null ? "" : selected.toString();
    }

    public void setCategories(List<String> categories) {
        categoryComboBox.removeAllItems();

        for (String category : categories) {
            categoryComboBox.addItem(category);
        }
    }

    public void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        nameField.requestFocusInWindow();
    }

    public void displayProducts(List<Product> products) {
        tableModel.setRowCount(0);

        for (Product p : products) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getCategory()
            });
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
