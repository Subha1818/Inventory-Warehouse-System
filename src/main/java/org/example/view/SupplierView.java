package org.example.view;

import org.example.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SupplierView extends JFrame {

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;

    private JButton addButton;
    private JButton refreshButton;

    private DefaultTableModel tableModel;
    private JTable supplierTable;

    public SupplierView() {
        setTitle("Supplier Management");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        nameField = new JTextField(15);
        phoneField = new JTextField(15);
        addressField = new JTextField(15);

        addButton = new JButton("Add Supplier");
        refreshButton = new JButton("Refresh");

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        formPanel.add(new JLabel("Supplier Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        formPanel.add(addButton);
        formPanel.add(refreshButton);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Phone", "Address"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        supplierTable = new JTable(tableModel);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(supplierTable), BorderLayout.CENTER);
    }

    public String getSupplierName() {
        return nameField.getText().trim();
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public String getAddress() {
        return addressField.getText().trim();
    }

    public void setAddListener(
            java.awt.event.ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void setRefreshListener(
            java.awt.event.ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public void displaySuppliers(List<Supplier> suppliers) {
        tableModel.setRowCount(0);

        for (Supplier supplier : suppliers) {
            tableModel.addRow(new Object[]{
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getPhone(),
                    supplier.getAddress()
            });
        }
    }

    public void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showWindow() {
        setVisible(true);
    }
}