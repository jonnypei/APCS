import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Flag extends JApplet {
    private final int STRIPES = 13;
    private final int STARS = 50;

    private final double A = 1.0;
    private final double B = 1.9;
    private final double C = 7.0 / STRIPES;
    private final double D = .76;
    private final double E = .054;
    private final double F = .054;
    private final double G = .063;
    private final double H = .063;
    private final double K = .0616;
    private final double L = 1.0 / STRIPES;
    private double flag_width;
    private double flag_height;
    private double stripe_height;

    // Added new main method so that my code works when called in terminal
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flag");
        Flag f = new Flag();
        frame.add(f);
        frame.setSize(760, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void init() {
        setSize(760, 400);
        repaint();
    }

    public void paint(Graphics g) {
        flag_width = getWidth();
        flag_height = getHeight();

        // Resizing of window
        if ((B / A) * flag_height > flag_width) {
            flag_height = (flag_width / (B / A));
        } else {
            flag_width = (flag_height * (B / A));
        }

        stripe_height = flag_height / STRIPES;

        drawBackground(g);
        drawStripes(g);
        drawField(g);
        drawStars(g);
    }

    // Draws the black background behind the flag
    private void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // Draws the stripes on the flag
    public void drawStripes(Graphics g) {
        Stripe red = new Stripe(Color.RED);
        Stripe white = new Stripe(Color.WHITE);
        Stripe s;

        for (int i = 0; i < STRIPES; i++) {
            s = (i % 2 == 0 ? red : white);
            s.draw(g, 0, (int) (i * stripe_height), (int) (flag_width), (int) (stripe_height));
        }
    }

    // Draws the blue rectangular area in the top left where the stars reside
    public void drawField(Graphics g) {
        Field f = new Field();
        int width = (int) (flag_width * D / B);
        int height = (int) (stripe_height * 7);
        f.draw(g, 0, 0, width, height);
    }

    // Draws the stars
    public void drawStars(Graphics g) {
        double xOffset = (G / B * flag_width);
        double yOffset = (E / A * flag_height);

        double xBtwn = (H / B * flag_width);
        double yBtwn = (F / A * flag_height);

        double radius = (K / B / 2 * flag_width);

        Star s = new Star();

        // Rows for 6 stars
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 6; col++) {
                s.draw(g, (int) (xOffset + col * 2 * xBtwn), (int) (yOffset + row * 2 * yBtwn), radius);
            }
        }

        // Rows for 5 stars
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                s.draw(g, (int) (xOffset + xBtwn + col * 2 * xBtwn), (int) (yOffset + yBtwn + row * 2 * yBtwn), radius);
            }
        }
    }
}
