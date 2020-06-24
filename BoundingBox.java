/*
 * Michael Ventoso <MichaelVentoso@Gmail.com>
 * Submitted 2 November 2017
 * Revised 24 June 2020
 * CMSC 201 Data Structures
 * Lab 7 BoundingBox
 *
 * This implementation of BoundingBox follows the provided API.
 * The top/bottom/left/right methods only take a constant amount of time.
 * To achieve this, most of the 'heavy lifting' is done in the add method.
 *
 * Main takes two integers as parameters:
 * Number of random points to plot
 * Size of the plot (forms a square with sides equal to input)
 *
 * The main will plot the given number of random points on a plot of the given size.  After adding all the points to the
 * BoundingBox and plotting them, a bounding box is drawn around the set of points.
 */

import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

public class BoundingBox {

    private static int W;               // width of plot
    private static int H;               // height of plot
    private static int C;               // store number of points in BoundingBox

    //Sets initial maxes/mins
    private static double xmaxd = Double.MIN_VALUE;
    private static double ymaxd = Double.MIN_VALUE;
    private static double xmind = Double.MAX_VALUE;
    private static double ymind = Double.MAX_VALUE;

    //Pointers to keep track of the uppermost/lowermost/leftmost/rightmost points
    private static Point2D xmaxp;
    private static Point2D ymaxp;
    private static Point2D xminp;
    private static Point2D yminp;

    //Creates a new BoundingBox with given width and height
    public BoundingBox(int width, int height){
        this.W = width;
        this.H = height;
        this.C = 0;
    }

    //If only one parameter is given, the the plot is assumed to be a square of the given size.
    public BoundingBox(int size){
        this(size,size);
    }

    //If no width and height are give, it defaults to size 1000x1000.
    public BoundingBox() {
        this(1000,1000);
    }

    /*
    Adds a Point2D to the BoundingBox.
    Does the heavy lifting here so that top/bottom/left/right all run in constant time (not dependant on number of points in the BoundingBox)
    Keeps track of how many points are in the BoundingBox
     */
    public void add(Point2D p){
        double xx = p.x();
        double yy = p.y();
        C++;
        if (xx > this.xmaxd){
            this.xmaxd = xx;

            this.xmaxp = p;
        }
        if (yy > this.ymaxd){
            this.ymaxd = yy;

            this.ymaxp = p;
        }
        if (xx < this.xmind){
            this.xmind = xx;

            this.xminp = p;
        }
        if (yy < this.ymind){
            this.ymind = yy;

            this.yminp = p;
        }
    }

    //Returns number of points in the BoundingBox
    public int size(){
        return C;
    }

    //Returns the topmost point
    public Point2D top(){
        return this.ymaxp;
    }

    //Returns the bottommost point
    public Point2D bottom(){
        return this.yminp;
    }

    //Returns the leftmost point
    public Point2D left(){
        return this.xminp;
    }

    //Returns the rightmost point
    public Point2D right(){
        return this.xmaxp;
    }

    //Returns the center of the bounding box as a Point2D.
    public Point2D centroid(){
        double cx = (this.xmaxp.x() + this.xminp.x())/2;
        double cy = (this.ymaxp.y() + this.yminp.y())/2;
        Point2D cen = new Point2D(cx,cy);
        return cen;
    }

    /*
    Main can be used for unit testing.
    As is it creates a new BoundingBox and fills it with N points.
    It then plots the points and draws the bounding box around them.
     */
    public static void main (String[] args){
        int N = Integer.parseInt(args[0]);
        BoundingBox box = new BoundingBox(Integer.parseInt(args[1]));

        StdDraw.setCanvasSize(W, H);
        StdDraw.setXscale(0, W);
        StdDraw.setYscale(0, H);
        StdDraw.setPenColor(StdDraw.GREEN);

        for (int i = 0; i < N; i ++){
            Point2D p = new Point2D(StdRandom.gaussian(W/2, W/8),
                    StdRandom.gaussian(H/2, H/8));
            box.add(p);
            StdDraw.filledCircle(p.x(), p.y(), 3);
        }

        Point2D left = box.left();
        Point2D right = box.right();
        Point2D top = box.top();
        Point2D bottom = box.bottom();
        StdDraw.setPenColor(StdDraw.ORANGE);
        StdDraw.line(right.x(), bottom.y(), right.x(), top.y());
        StdDraw.line(left.x(), bottom.y(), left.x(), top.y());
        StdDraw.line(left.x(), bottom.y(), right.x(), bottom.y());
        StdDraw.line(left.x(), top.y(), right.x(), top.y());
    }
}
