import java.awt.Color;
import java.awt.Graphics;

public class Star {
    private Color color;
    private final int numPoints = 10;
    private int[] x = new int[numPoints];
    private int[] y = new int[numPoints];

    public Star() {
        color = Color.WHITE;
    }

    public void draw(Graphics g, int centerX, int centerY, double radius) {
        double innerRadius = radius * Math.sin(Math.toRadians(18) / Math.sin(Math.toRadians(54)));

        // Outer Points of the Star
        for (int i = 18; i < 360; i += 72) {
            x[(i - 18) / 36] = centerX + (int) (radius * Math.cos(Math.toRadians(i)));
            y[(i - 18) / 36] = centerY - (int) (radius * Math.sin(Math.toRadians(i)));
        }

        // Inner Points of the Star
        for (int i = 54; i < 360; i += 72) {
            x[(i - 18) / 36] = centerX + (int) (innerRadius * Math.cos(Math.toRadians(i)));
            y[(i - 18) / 36] = centerY - (int) (innerRadius * Math.sin(Math.toRadians(i)));
        }

        Color c = g.getColor();
        g.setColor(color);
        g.fillPolygon(x, y, numPoints);
        g.setColor(c);
    }

}