import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 29/02/16.
 */
public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        this.points = new SET();
    }

    /**
     *
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return this.points.isEmpty();
    }

    /**
     *
     * @return number of points in the set
     */
    public int size() {
        return this.points.size();
    }

    public void insert(Point2D p) {
        if ( p == null)
            throw new NullPointerException();
        else
            this.points.add(p);
    }

    public boolean contains(Point2D p) {
        if ( p == null)
            throw new NullPointerException();
        else
            return this.points.contains(p);
    }

    public void draw() {
        for(Point2D p : points){
            StdDraw.point(p.x(),p.y());
        }
    }

    /**
     *
     * @param rect
     * @return all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> result = new ArrayList<>();
        for(Point2D p : points){
            if(p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax())
                result.add(p);
        }
        return result;
    }

    /**
     *
     * @param p
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        Point2D minNeighbor = null;
        double minDist = Double.MAX_VALUE;
        for(Point2D neighbor : points){
            double v = p.distanceSquaredTo(neighbor);
            if (v <= minDist){
                minDist = v;
                minNeighbor = neighbor;
            }
        }
        return minNeighbor;
    }

    public static void main(String[] args){}

}
