package com.joysis.tvi.JobFit.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIComponents {

    public static RoundedPanel createActionCard(String title, String description) {

        RoundedPanel card = new RoundedPanel(22);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // TITLE ROW (ICON + TITLE aligned)
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleRow.setOpaque(false);
        titleRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel iconLabel = new JLabel(getCardIcon(title));
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
        iconLabel.setForeground(UITheme.BLUE);
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(UITheme.TEXT);

        titleRow.add(iconLabel);
        titleRow.add(titleLabel);

        JLabel descLabel = new JLabel("<html><div style='width:280px;'>" + description + "</div></html>");
        descLabel.setFont(UITheme.BODY);
        descLabel.setForeground(UITheme.TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descLabel.setBorder(new EmptyBorder(12, 32, 0, 0));

        contentPanel.add(titleRow);
        contentPanel.add(descLabel);

        JPanel arrowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        arrowPanel.setOpaque(false);

        JLabel arrowLabel = new JLabel("→");
        arrowLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        arrowLabel.setForeground(UITheme.BLUE);

        arrowPanel.add(arrowLabel);

        card.add(contentPanel, BorderLayout.CENTER);
        card.add(arrowPanel, BorderLayout.EAST);

        return card;
    }


    public static JLabel createSectionTitle(
            String title
    ) {

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(UITheme.TEXT);

        return label;
    }

    public static JPanel createClickableActionCard(
            String title,
            String description,
            String iconText,
            Runnable action
    ) {

        RoundedPanel card = new RoundedPanel(UITheme.CARD_RADIUS);
        card.setBackground(UITheme.SURFACE);

        card.setLayout(new BorderLayout(12, 0));
        card.setBorder(new EmptyBorder(
                        16,
                        18,
                        16,
                        16));

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ==========================================
        // ICON
        // ==========================================

        JLabel iconLabel = new JLabel(iconText, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        iconLabel.setForeground(UITheme.BLUE);
        iconLabel.setPreferredSize(new Dimension(34, 34));

        // ==========================================
        // TEXT
        // ==========================================

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(UITheme.TEXT);
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(UITheme.SMALL);

        descriptionLabel.setForeground(UITheme.TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(Box.createVerticalGlue());

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(descriptionLabel);
        textPanel.add(Box.createVerticalGlue());

        // ==========================================
        // ARROW
        // ==========================================

        JLabel arrow = new JLabel("→", SwingConstants.CENTER);
        arrow.setFont(new Font("Segoe UI", Font.BOLD, 20));
        arrow.setForeground(UITheme.BLUE);
        arrow.setPreferredSize(new Dimension(28, 28));
        card.add(iconLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(arrow, BorderLayout.EAST);

        // ==========================================
        // HOVER + CLICK
        // ==========================================

        java.awt.event.MouseAdapter mouseAdapter =
                new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(232, 244, 255));

                titleLabel.setForeground(UITheme.BLUE);
                iconLabel.setForeground(UITheme.CYAN);
                arrow.setForeground(UITheme.CYAN);
                card.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                card.setBackground(UITheme.SURFACE);
                titleLabel.setForeground(UITheme.TEXT);
                iconLabel.setForeground(UITheme.BLUE);
                arrow.setForeground(UITheme.BLUE);
                card.repaint();
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (action != null) {action.run();
                }
            }
        };

        card.addMouseListener(mouseAdapter);
        iconLabel.addMouseListener(mouseAdapter);
        textPanel.addMouseListener(mouseAdapter);
        titleLabel.addMouseListener(mouseAdapter);
        descriptionLabel.addMouseListener(mouseAdapter);
        arrow.addMouseListener(mouseAdapter);

        return card;
    }

    private static String getCardIcon(String title) {
        String text = title.toLowerCase();

        if (text.contains("user")) {
            return "👥";
        } else if (text.contains("skill")) {
            return "⚙";
        } else if (text.contains("job")) {
            return "💼";
        } else if (text.contains("report")) {
            return "📊";
        }

        return "•";
    }
    private static String formatDisplayName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }

        String[] parts = name.trim().toLowerCase().split("\\s+");
        StringBuilder builder = new StringBuilder();

        for (String part : parts) {
            builder.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1))
                    .append(" ");
        }

        return builder.toString().trim();
    }



}




