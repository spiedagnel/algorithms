/**
 * Created by samue_000 on 11/02/2016.
 */
public class BruteCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points[0].slopeTo(points[1]) == points[0].slopeTo(points[2])){
            if (points[0].slopeTo(points[1]) == points[0].slopeTo(points[3])){

            }

        }
    }
    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments;
    }

}
