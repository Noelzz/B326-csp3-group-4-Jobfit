package com.joysis.tvi.JobFit.config;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private static final Color DEFAULT_BG = new Color(45, 95, 170);
    private static final Color DEFAULT_HOVER = new Color(35, 78, 145);

    private final Color baseColor;
    private final Color hoverColor;
    private boolean hovering = false;

    public RoundedButton(String text) {
        this(text, DEFAULT_BG, DEFAULT_HOVER);
    }

    public RoundedButton(String text, Color baseColor, Color hoverColor) {
        super(text);
        this.baseColor = baseColor;
        this.hoverColor = hoverColor;

        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovering = true;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovering = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(hovering ? hoverColor : baseColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g2.dispose();
        super.paintComponent(g);
    }
}