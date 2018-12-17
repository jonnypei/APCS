import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

public class ConvexHullGS extends JPanel {

    final static int length = 800;                                // Side length of Display Window
    final static int shift = length / 2;                          // Orientation shift of coordinates
    final static int pointR = 3;                                  // Radius of Coordinate Point`
    static double multiplier;                                     // Coordinate Scaling Factor

    static ArrayList <Point> points = new ArrayList <> ();        // Input Points
    static ArrayList <Point> hull = new ArrayList <> ();          // Points on the Convex Hull


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
        hull = getConvexHull(points);

        // Determine scaling of coordinates
        double maxCoordinate = 0;
        for (Point2D p : hull) {
            maxCoordinate = Math.max(p.getX(), maxCoordinate);
            maxCoordinate = Math.max(p.getY(), maxCoordinate);
        }
        multiplier = length / (maxCoordinate * 3);

        // Display the Convex Hull in the Console
        System.out.println("Convex Hull Solution: ");
        for (Point2D p : hull) {
            System.out.println("(" + p.getX() + ", " + p.getY() + ")");
        }
        System.out.println("\nSwitch to the Display Window to see your Convex Hull!");

        // Graphically display the Convex Hull
        ConvexHullGS hull = new ConvexHullGS();

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
            g2d.fillOval(orientX(p.getX()) - pointR, orientY(p.getY()) - pointR, 2 * pointR, 2 * pointR);
            g2d.drawString("(" + p.getX() + ", " + p.getY() + ")", orientX(p.getX()) + 3, orientY(p.getY()) - 5);
        }

        // Draw Convex Hull
        for (int i = 0; i < hull.size(); i++) {
            Point2D p1 = hull.get(i);
            Point2D p2 = hull.get((i + 1) % hull.size());

            g2d.drawLine(orientX(p1.getX()), orientY(p1.getY()), orientX(p2.getX()), orientY(p2.getY()));
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

    protected static enum Turn { CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR }

    protected static boolean areAllCollinear(ArrayList <Point> points) {

        if (points.size() < 2) {
            return true;
        }

        final Point a = points.get(0);
        final Point b = points.get(1);

        for (int i = 2; i < points.size(); i++) {

            Point c = points.get(i);

            if (getTurn(a, b, c) != Turn.COLLINEAR) {
                return false;
            }
        }

        return true;
    }

    public static ArrayList <Point> getConvexHull(List <Point> points) throws IllegalArgumentException {

        ArrayList <Point> sorted = new ArrayList <Point> (getSortedPointSet(points));

        Stack <Point> stack = new Stack <Point> ();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {

            Point head = sorted.get(i);
            Point middle = stack.pop();
            Point tail = stack.peek();

            Turn turn = getTurn(tail, middle, head);

            switch(turn) {
                case COUNTER_CLOCKWISE:
                    stack.push(middle);
                    stack.push(head);
                    break;
                case CLOCKWISE:
                    i--;
                    break;
                case COLLINEAR:
                    stack.push(head);
                    break;
            }
        }

        // close the hull
        stack.push(sorted.get(0));

        return new ArrayList<Point>(stack);
    }

    protected static Point getLowestPoint(List<Point> points) {
        Point lowest = points.get(0);

        for(int i = 1; i < points.size(); i++) {

            Point temp = points.get(i);

            if(temp.y < lowest.y || (temp.y == lowest.y && temp.x < lowest.x)) {
                lowest = temp;
            }
        }

        return lowest;
    }

    protected static Set<Point> getSortedPointSet(List<Point> points) {

        final Point lowest = getLowestPoint(points);

        TreeSet<Point> set = new TreeSet <Point> (new Comparator<Point> () {
            public int compare(Point a, Point b) {

                if(a == b || a.equals(b)) {
                    return 0;
                }

                double thetaA = Math.atan2(a.y - lowest.y, a.x - lowest.x);
                double thetaB = Math.atan2(b.y - lowest.y, b.x - lowest.x);

                if (thetaA < thetaB) {
                    return -1;
                } else if (thetaA > thetaB) {
                    return 1;
                } else {
                    // collinear with the 'lowest' point, let the point closest to it come first

                    // use longs to guard against int-over/underflow
                    double distanceA = Math.sqrt(((lowest.x - a.x) * (lowest.x - a.x)) +
                            ((lowest.y - a.y) * (lowest.y - a.y)));
                    double distanceB = Math.sqrt(((lowest.x - b.x) * (lowest.x - b.x)) +
                            ((lowest.y - b.y) * (lowest.y - b.y)));

                    if( distanceA < distanceB) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });

        set.addAll(points);

        return set;
    }

    protected static Turn getTurn(Point a, Point b, Point c) {
        long crossProduct = ((b.x - a.x) * (c.y - a.y)) -
                ((b.y - a.y) * (c.x - a.x));

        if(crossProduct > 0) {
            return Turn.COUNTER_CLOCKWISE;
        }
        else if(crossProduct < 0) {
            return Turn.CLOCKWISE;
        }
        else {
            return Turn.COLLINEAR;
        }
    }
}
