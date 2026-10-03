package com.joysis.tvi.JobFit.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {

    private final Color startColor;
    private final Color endColor;
    private final Color hoverStartColor;
    private final Color hoverEndColor;

    private boolean hovered = false;
    private boolean showArrow;

    public RoundedButton(
            String text,
            Color normalColor,
            Color hoverColor
    ) {
        this(
                text,
                normalColor,
                brighten(normalColor, 25),
                hoverColor,
                brighten(hoverColor, 25),
                false);
    }

    public RoundedButton(
            String text,
            Color startColor,
            Color endColor,
            Color hoverStartColor,
            Color hoverEndColor,
            boolean showArrow
    ) {

        super(text);

        this.startColor = startColor;
        this.endColor = endColor;
        this.hoverStartColor = hoverStartColor;
        this.hoverEndColor = hoverEndColor;
        this.showArrow = showArrow;

        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 15));

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        }

        );
    }

    public void setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        Color color1 = hovered ? hoverStartColor : startColor;
        Color color2 = hovered ? hoverEndColor : endColor;

        // Shadow
        g2.setColor(new Color(0, 75, 190, 65));
        g2.fillRoundRect(
                4,
                6,
                width - 8,
                height - 8,
                18,
                18);

        // Gradient
        GradientPaint gradient =
                new GradientPaint(
                        0,
                        0,
                        color1,
                        width,
                        0,
                        color2);

        g2.setPaint(gradient);
        g2.fillRoundRect(
                0,
                0,
                width - 7,
                height - 8,
                18,
                18);

        // Soft border
        g2.setColor(new Color(80, 220, 255, 150));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(
                0,
                0,
                width - 8,
                height - 9,
                18,
                18);

        // Text + arrow
        FontMetrics fm = g2.getFontMetrics(getFont());
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int arrowWidth = showArrow ? 13 : 0;
        int gap = showArrow ? 15 : 0;
        int totalWidth = textWidth + gap + arrowWidth;
        int startX = (width - totalWidth) / 2;
        int textY = (height - fm.getHeight()) / 2 + fm.getAscent() - 3;
        g2.setFont(getFont());
        g2.setColor(getForeground());
        g2.drawString(text, startX, textY);

        if (showArrow) {
            int arrowX = startX + textWidth + gap;

            int arrowY = height / 2 - 4;
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(arrowX, arrowY, arrowX + 10, arrowY);

            g2.drawLine(arrowX + 6, arrowY - 4, arrowX + 10, arrowY);

            g2.drawLine(arrowX + 6, arrowY + 4, arrowX + 10, arrowY);
        }

        g2.dispose();
    }

    private static Color brighten(Color color, int amount
    ) {

        return new Color(Math.min(255, color.getRed() + amount), Math.min(255, color.getGreen() + amount), Math.min(255, color.getBlue() + amount));
    }
}