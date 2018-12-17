import javax.swing.*;
import java.util.*;
import java.io.*;

import java.awt.*;

public class ConvexHullBetter extends JPanel {

    final static int length = 800;                                // Side length of Display Window
    final static int shift = length / 2;                          // Orientation shift of coordinates
    final static int pointR = 3;                                  // Radius of Coordinate Point`
    static double multiplier;                                     // Coordinate Scaling Factor

    static ArrayList <Point2D> points = new ArrayList <> ();      // Input Points
    public static Stack <Point2D> hull = new Stack <> ();            // Points on the Convex Hull


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("data.txt"));

        // Read in all the points from the input file
        StringTokenizer st;
        while (in.ready()) {
            st = new StringTokenizer(in.readLine());

            int x = Integer.parseInt(trim(st.nextToken()));
            int y = Integer.parseInt(st.nextToken());

            points.add(new Point2D(x, y));
        }
        in.close();

        // Find Convex Hull
        findHull(points);

        // Determine scaling of coordinates
        double maxCoordinate = 0;
        for (Point2D p : hull()) {
            maxCoordinate = Math.max(p.x, maxCoordinate);
            maxCoordinate = Math.max(p.y, maxCoordinate);
        }
        multiplier = length / (maxCoordinate * 3);

        // Display the Convex Hull in the Console
        System.out.println("Convex Hull Solution: ");
        for (Point2D p : hull()) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
        System.out.println("\nSwitch to the Display Window to see your Convex Hull!");

        // Graphically display the Convex Hull
        ConvexHull hull = new ConvexHull();

        JFrame frame = new JFrame("Convex Hull");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(hull);
        frame.setSize(length, length);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);

        // Draw the x and y axes
        g2d.drawLine(shift, 0, shift, length);
        g2d.drawLine(0, shift, length, shift);

        g2d.setColor(Color.blue);

        // Plot all points (Including those not on the Convex Hull)
        for (Point2D p : points) {
            g2d.fillOval(orientX(p.x) - pointR, orientY(p.y) - pointR, 2 * pointR, 2 * pointR);
            g2d.drawString("(" + p.x + ", " + p.y + ")", orientX(p.x) + 3, orientY(p.y) - 5);
        }

        // Draw Convex Hull
        for (int i = 0; i < hull.size(); i++) {
            Point2D p1 = hull.get(i);
            Point2D p2 = hull.get((i + 1) % hull.size());

            g2d.drawLine(orientX(p1.x), orientY(p1.y), orientX(p2.x), orientY(p2.y));
        }
    }

    static int orientX(double x) {
        return (int) ((x * multiplier) + shift);
    }

    static int orientY(double y) {
        return (int) (shift - (y * multiplier));
    }

    // Trim last character of a string
    static String trim(String s) {
        return s.substring(0, s.length() - 1);
    }

    public static void findHull(ArrayList <Point2D> points) {
        int n = points.size();
        Point2D[] pts = new Point2D[n];
        for (int i = 0; i < n; i++) {
            if (points.get(i) == null)
                throw new IllegalArgumentException("points[" + i + "] is null");
            pts[i] = points.get(i);
        }

        Arrays.sort(pts);

        Arrays.sort(pts, 1, n, pts[0].polarOrder());

        hull.push(pts[0]);

        int i;
        for (i = 1; i < n; i++)
            if (!pts[0].equals(pts[i])) break;
        if (i == n) return;

        int j;
        for (j = i + 1; j < n; j++)
            if (Point2D.ccw(pts[0], pts[i], pts[j]) != 0) break;
        hull.push(pts[j - 1]);

        for (int k = j; k < n; k++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, pts[k]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(pts[i]);
        }

        assert isConvex();
    }

    public static Iterable <Point2D> hull() {
        Stack <Point2D> s = new Stack <> ();
        for (Point2D p : hull) s.push(p);
        return s;
    }

    // check that boundary of hull is strictly convex
    public static boolean isConvex() {
        int n = hull.size();
        if (n <= 2) return true;

        Point2D[] points = new Point2D[n];
        int k = 0;
        for (Point2D p : hull()) {
            points[k++] = p;
        }

        for (int i = 0; i < n; i++) {
            if (Point2D.ccw(points[i], points[(i + 1) % n], points[(i + 2) % n]) <= 0) {
                return false;
            }
        }
        return true;
    }

    // Found this at https://algs4.cs.princeton.edu/13stacks/Point2D.java.html
    public static class Point2D implements Comparable<Point2D> {
        private final double x;    // x coordinate
        private final double y;    // y coordinate

        public Point2D(double x, double y) {
            if (Double.isInfinite(x) || Double.isInfinite(y))
                throw new IllegalArgumentException("Coordinates must be finite");
            if (Double.isNaN(x) || Double.isNaN(y))
                throw new IllegalArgumentException("Coordinates cannot be NaN");
            if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
            else this.x = x;

            if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
            else this.y = y;
        }

        public static int ccw(Point2D a, Point2D b, Point2D c) {
            double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
            if (area2 < 0) return -1;
            else if (area2 > 0) return +1;
            else return 0;
        }

        public int compareTo(Point2D that) {
            if (this.y < that.y) return -1;
            if (this.y > that.y) return +1;
            if (this.x < that.x) return -1;
            if (this.x > that.x) return +1;
            return 0;
        }

        public Comparator<Point2D> polarOrder() {
            return new PolarOrder();
        }

        private class PolarOrder implements Comparator<Point2D> {
            public int compare(Point2D q1, Point2D q2) {
                double dx1 = q1.x - x;
                double dy1 = q1.y - y;
                double dx2 = q2.x - x;
                double dy2 = q2.y - y;

                if (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
                else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
                else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                    if (dx1 >= 0 && dx2 < 0) return -1;
                    else if (dx2 >= 0 && dx1 < 0) return +1;
                    else return 0;
                } else return -ccw(Point2D.this, q1, q2);     // both above or below

                // Note: ccw() recomputes dx1, dy1, dx2, and dy2
            }
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) return true;
            if (other == null) return false;
            if (other.getClass() != this.getClass()) return false;
            Point2D that = (Point2D) other;
            return this.x == that.x && this.y == that.y;
        }
    }
}