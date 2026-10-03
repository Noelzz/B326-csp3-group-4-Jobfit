package com.joysis.tvi.JobFit.view;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private final int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Shadow
        g2.setColor(new Color(0, 45, 110, 28));
        g2.fillRoundRect(6, 8, w - 12, h - 12, radius, radius);

        // Main panel
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w - 10, h - 10, radius, radius);

        // Soft border
        g2.setColor(new Color(220, 232, 248));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0, 0, w - 11, h - 11, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}