
package org.example.view;

import org.example.model.Product;
import org.example.model.StockTransaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StockView extends JFrame {

    private JComboBox<Product> productComboBox;
    private JComboBox<String> typeComboBox;
    private JTextField quantityField;

    private JButton recordButton;
    private JButton refreshButton;

    private DefaultTableModel tableModel;
    private JTable transactionTable;

    public StockView() {
        setTitle("Stock Inward / Outward");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        productComboBox = new JComboBox<>();
        typeComboBox = new JComboBox<>(
                new String[]{"IN", "OUT"}
        );
        quantityField = new JTextField(10);

        recordButton = new JButton("Record Stock");
        refreshButton = new JButton("Refresh");

        JPanel formPanel = new JPanel(new FlowLayout());

        formPanel.add(new JLabel("Product:"));
        formPanel.add(productComboBox);

        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeComboBox);

        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);

        formPanel.add(recordButton);
        formPanel.add(refreshButton);

        tableModel = new DefaultTableModel(
                new String[]{
                        "Transaction ID", "Product ID",
                        "Product Name", "Type",
                        "Quantity", "Date"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new JTable(tableModel);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
    }

    public Product getSelectedProduct() {
        return (Product) productComboBox.getSelectedItem();
    }

    public String getTransactionType() {
        return (String) typeComboBox.getSelectedItem();
    }

    public String getQuantityText() {
        return quantityField.getText().trim();
    }

    public void setProducts(List<Product> products) {
        productComboBox.removeAllItems();

        for (Product product : products) {
            productComboBox.addItem(product);
        }
    }

    public void displayTransactions(
            List<StockTransaction> transactions) {

        tableModel.setRowCount(0);

        for (StockTransaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                    transaction.getId(),
                    transaction.getProductId(),
                    transaction.getProductName(),
                    transaction.getType(),
                    transaction.getQuantity(),
                    transaction.getDate()
            });
        }
    }

    public void setRecordListener(
            java.awt.event.ActionListener listener) {
        recordButton.addActionListener(listener);
    }

    public void setRefreshListener(
            java.awt.event.ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public void clearQuantity() {
        quantityField.setText("");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showWindow() {
        setVisible(true);
    }
}
