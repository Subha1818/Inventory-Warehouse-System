package InventorySystem.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class UITheme {

    public static final Color PRIMARY = new Color(0, 128, 128);
    public static final Color PRIMARY_DARK = new Color(0, 90, 90);
    public static final Color BACKGROUND = new Color(245, 247, 248);
    public static final Color TEXT = new Color(45, 45, 45);
    public static final Color LIGHT_TEAL = new Color(220, 239, 239);
    public static final Color BORDER = new Color(215, 222, 224);

    private UITheme() {
    }

    public static void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(primary ? Color.WHITE : TEXT);
        button.setBackground(primary ? PRIMARY : LIGHT_TEAL);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(new CompoundBorder(
                new LineBorder(primary ? PRIMARY_DARK : BORDER),
                new EmptyBorder(6, 10, 6, 10)
        ));
    }

    public static void styleInput(JComponent input) {
        input.setFont(new Font("Arial", Font.PLAIN, 13));
        input.setForeground(TEXT);
        input.setBackground(Color.WHITE);
        input.setBorder(new CompoundBorder(
                new LineBorder(BORDER),
                new EmptyBorder(4, 6, 4, 6)
        ));
    }

    public static void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setGridColor(BORDER);
        table.setRowHeight(24);
        table.setSelectionBackground(LIGHT_TEAL);
        table.setSelectionForeground(TEXT);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(LIGHT_TEAL);
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setBorder(new LineBorder(BORDER));
    }
}
