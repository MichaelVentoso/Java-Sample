/*
 * Michael Ventoso <MichaelVentoso@Gmail.com>
 * Submitted 2 November 2017
 * Revised 24 June 2020
 * CMSC 201 Data Structures
 * Lab 7 ConvexHull
 *
 ************** I did not write this code!  It was provided to test our Stack2 and MinPQMultiway, and ensure we implemented the API's given.***************
 * Main takes three integers as parameters:
 * Width of the plot
 * Height of the plot
 * The number of random points to be plotted
 *
 * The ConvexHull main plots a given number of points on a plot of the given size.
 * It then draws a convex hull around the points.
 */

import edu.princeton.cs.algs4.*;

public class ConvexHull{

    public static void main (String[] args){
        int W = Integer.parseInt(args[0]);
        int H = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[2]);

        Stopwatch timer = new Stopwatch();
        MinPQMultiway<Point2D> yPoints = new MinPQMultiway<Point2D>(8, N);

        StdDraw.setCanvasSize(W, H);
        StdDraw.setXscale(0,W);
        StdDraw.setYscale(0, H);
        StdDraw.setPenColor(StdDraw.GREEN);

        for (int i = 0; i < N; i ++){
            Point2D p = new Point2D(StdRandom.gaussian(W/2, W/8),
                    StdRandom.gaussian(H/2, H/8));
            yPoints.insert(p);
            StdDraw.filledCircle(p.x(), p.y(), 3);
        }

        Point2D p0 = yPoints.delMin();

        MinPQMultiway<Point2D> pPoints = new MinPQMultiway<Point2D>(p0.polarOrder(),N);

        while (!yPoints.isEmpty()){
            pPoints.insert(yPoints.delMin());
        }

        Stack2<Point2D> s = new Stack2<Point2D>();

        s.push(p0);
        s.push(pPoints.delMin());
        s.push(pPoints.delMin());

        while (!pPoints.isEmpty()){
            Point2D pi = pPoints.delMin();
            while (s.size() > 1 && Point2D.ccw(s.sneakPeek(), s.peek(), pi) == -1) {
                s.pop();
            }
            s.push(pi);
        }
        drawHull(s);
        StdOut.println("elapsed time = " + timer.elapsedTime());
    }

    public static void drawHull(Stack2<Point2D> s){
        StdDraw.setPenColor(StdDraw.ORANGE);
        Point2D first = s.pop();
        Point2D last = first;
        while (!s.isEmpty()){
            Point2D q = s.pop();
            StdDraw.line(last.x(), last.y(), q.x(), q.y());
            last = q;
        }
        StdDraw.line(first.x(), first.y(), last.x(), last.y());
    }

}
