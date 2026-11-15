package InventorySystem.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DashboardView extends JPanel {

    private final JLabel totalProductsValue;
    private final JLabel totalStockUnitsValue;
    private final JLabel registeredSuppliersValue;
    private final JLabel totalCategoriesValue;

    public DashboardView() {
        setLayout(new GridLayout(1, 4, 12, 0));
        setBackground(UITheme.BACKGROUND);
        setBorder(new EmptyBorder(5, 15, 10, 15));
        setPreferredSize(new Dimension(900, 88));

        totalProductsValue = addCard("Total Products");
        totalStockUnitsValue = addCard("Total Stock Units");
        registeredSuppliersValue = addCard("Registered Suppliers");
        totalCategoriesValue = addCard("Total Categories");
    }

    private JLabel addCard(String title) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(UITheme.BORDER),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(UITheme.TEXT);

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
        valueLabel.setForeground(UITheme.PRIMARY_DARK);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        add(card);
        return valueLabel;
    }

    public void displayStatistics(
            int totalProducts,
            int totalStockUnits,
            int registeredSuppliers,
            int totalCategories) {
        totalProductsValue.setText(String.valueOf(totalProducts));
        totalStockUnitsValue.setText(String.valueOf(totalStockUnits));
        registeredSuppliersValue.setText(String.valueOf(registeredSuppliers));
        totalCategoriesValue.setText(String.valueOf(totalCategories));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Dashboard Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
