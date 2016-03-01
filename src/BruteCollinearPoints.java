import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by samue_000 on 11/02/2016.
 */
public class BruteCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if ( points == null)
            throw new NullPointerException();
        List<LineSegment> list = new ArrayList<>();
        for ( int i = 0; i < points.length ; i++) {
            for (int j = i + 1 ; j < points. length; j ++) {
                for (int k = j + 1 ; k < points. length; k ++) {
                    for (int l = k + 1; l < points. length; l ++) {
                        if (points[i] == null || points [j] == null || points[k] == null || points[l] == null)
                            throw new IllegalArgumentException();
                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[i].slopeTo(points[k]);
                        double s3 = points[i].slopeTo(points[l]);
                        if (s1 == s2 && s1 == s3){
                            Point[] s = new Point[]{points[i],points[j],points[k],points[l]};
                            Arrays.sort(s);
                            numberOfSegments ++;
                            list.add(new LineSegment(s[0],s[3]));
                        }
                    }
                }
            }
        }
        segments = list.toArray(new LineSegment[list.size()]);
    }
    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return segments;
    }

}
