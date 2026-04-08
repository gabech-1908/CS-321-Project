import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PieChartPanel extends JPanel {
    private final String[] labels;
    private final double[] values;
    private final Color[] colors;

    public PieChartPanel(String[] labels, double[] values, Color[] colors) {
        this.labels = labels;
        this.values = values;
        this.colors = colors;
        setPreferredSize(new Dimension(430, 320));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Reserve room on the right for the legend while keeping the pie responsive.
        int diameter = Math.min(width - 180, height - 40);
        diameter = Math.max(diameter, 140);
        int x = 20;
        int y = (height - diameter) / 2;

        // Compute total spending so each slice angle can be normalized to 360 degrees.
        double total = 0;
        for (double value : values) {
            total += value;
        }

        if (total <= 0) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("No transaction data available", x, y + (diameter / 2));
            g2d.dispose();
            return;
        }

        int startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            int angle = (int) Math.round((values[i] / total) * 360);
            g2d.setColor(colors[i % colors.length]);
            g2d.fillArc(x, y, diameter, diameter, startAngle, angle);
            startAngle += angle;
        }

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawOval(x, y, diameter, diameter);

        // Draw a compact legend showing category name and percent contribution.
        int legendX = x + diameter + 20;
        int legendY = y + 10;
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 14));

        for (int i = 0; i < labels.length; i++) {
            double percent = (values[i] / total) * 100.0;
            g2d.setColor(colors[i % colors.length]);
            g2d.fillRect(legendX, legendY + (i * 28), 16, 16);

            g2d.setColor(Color.BLACK);
            String line = labels[i] + " (" + String.format("%.1f", percent) + "%)";
            g2d.drawString(line, legendX + 24, legendY + 13 + (i * 28));
        }

        g2d.dispose();
    }
}