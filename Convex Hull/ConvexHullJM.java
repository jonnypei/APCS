import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.*;

public class ConvexHullJM extends JPanel {

    static int width;                                             // Width of Display Window
    static int height;                                            // Height of Display Window
    static int shiftX;                                            // Orientation shift of X coordinate
    static int shiftY;                                            // Orientation shift of Y coordinate
    static double maxCoordinate;                                  // Highest x or y coordinate value
    final static int pointR = 3;                                  // Radius of Coordinate Point`
    static double multiplier;                                     // Coordinate Scaling Factor

    static ArrayList <Point> points = new ArrayList <> ();        // Input Points
    static ArrayList <Point> finalPoints = new ArrayList <> ();   // Points on the Convex Hull

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("data.txt"));

        // Read in all the points from the input file
        StringTokenizer st;
        while (in.ready()) {
            st = new StringTokenizer(in.readLine());

            int x = Integer.parseInt(trim(st.nextToken()));
            int y = Integer.parseInt(st.nextToken());

            points.add(new Point(x, y));
        }
        in.close();

        // Find Convex Hull
        finalPoints = convexHull(points, points.size());

        // Determine scaling of coordinates
        maxCoordinate = 0;
        for (Point p : finalPoints) {
            maxCoordinate = Math.max(p.x, maxCoordinate);
            maxCoordinate = Math.max(p.y, maxCoordinate);
        }

        // Display the Convex Hull in the Console
        System.out.println("Convex Hull Solution: ");
        for (Point p : finalPoints) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
        System.out.println("\nSwitch to the Display Window to see your Convex Hull!");

        // Graphically display the Convex Hull
        ConvexHullJM hull = new ConvexHullJM();

        JFrame frame = new JFrame("Convex Hull");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(hull);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        width = getWidth();
        height = getHeight();
        shiftX = width / 2;
        shiftY = height / 2;
        multiplier = Math.min(width, height) / (3 * maxCoordinate);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);

        // Draw the x and y axes
        g2d.drawLine(shiftX, 0, shiftX, height);
        g2d.drawLine(0, shiftY, width, shiftY);

        g2d.setColor(Color.blue);

        // Plot all points (Including those not on the Convex Hull)
        for (Point p : points) {
            g2d.fillOval(orientX(p.x) - pointR, orientY(p.y) - pointR, 2 * pointR, 2 * pointR);
            g2d.drawString("(" + p.x + ", " + p.y + ")", orientX(p.x) + 3, orientY(p.y) - 5);
        }

        // Draw Convex Hull
        for (int i = 0; i < finalPoints.size(); i++) {
            Point p1 = finalPoints.get(i);
            Point p2 = finalPoints.get((i + 1) % finalPoints.size());

            g2d.drawLine(orientX(p1.x), orientY(p1.y), orientX(p2.x), orientY(p2.y));
        }
    }

    static int orientX(int x) {
        return (int) (x * multiplier) + shiftX;
    }

    static int orientY(int y) {
        return (int) (shiftY - (y * multiplier));
    }

    // Trim last character of a string
    static String trim(String s) {
        return s.substring(0, s.length() - 1);
    }

    // Find cross product of vectors ab and bc
    public static int cross(Point a, Point b, Point c) {
        return (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
    }

    // Find Convex Hull
    public static ArrayList <Point> convexHull(ArrayList <Point> v, int size)  {
        ArrayList <Point> hull = new ArrayList <> ();

        // No Convex Hull if less than three points
        if (size < 3)
            return hull;

        Collections.sort(v, new Comparator <Point> () {
            public int compare(Point p, Point pOther) {
                return pOther.x - p.x;
            }
        });

        // Find left-most point
        int start = size - 1;

        // Start at the left-most point and scan through the outer-most points of the set
        // Eventually, we will return to the first point, at which we will have finished
        int curr = start;
        int next;
        do {
            hull.add(v.get(curr));

            next = (curr + 1) % size;

            for (int i = 0; i < size; i++) {
                if (cross(v.get(curr), v.get(i), v.get(next)) > 0)
                    next = i;
            }

            curr = next;

        } while (curr != start);

        return hull;
    }
}
