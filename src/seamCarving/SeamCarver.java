package seamCarving;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;

import java.awt.*;

/**
 * Created by samuel on 03/04/16.
 */
public class SeamCarver {

    private int[][] current;
    private int height;
    private int width;



    private class Point implements Comparable<Point>{
        private int x;
        private int y;
        private double dist;

        public Point(int x, int y, double dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public int compareTo(Point o) {
            if(this.dist < o.dist)
                return -1;
            if(this.dist > o.dist)
                return +1;
            return 0;
        }
    }

    /**
     *  create a seam carver object based on the given picture
     * @param picture
     */
    public SeamCarver(Picture picture){
        this.current = copy(picture);
        width = picture.width();
        height = picture.height();
    }

    private int[][] copy(Picture original){
        int[][] copy = new int[original.width()][original.height()];
        for (int x = 0; x < original.width(); x++){
            for (int y = 0; y < original.height(); y++){
                copy[x][y] = original.get(x,y).getRGB();
            }
        }
        return copy;
    }

    /**
     *
     * @return the current picture
     */
    public Picture picture() {
        Picture p = new Picture(width(),height());
        for (int x =0 ; x < width() ; x++){
            for (int y =0; y < height() ; y++)
                p.set(x,y,new Color(current[x][y]));
        }
        return p;
    }

    /**
     *
     * @return width of current picture
     */
    public     int width() {
        return width;
    }

    /**
     *
     * @return height of current picture
     */
    public     int height() {
        return height;
    }

    /**
     *
     * @param x
     * @param y
     * @return energy of pixel at column x and row y
     */
    public  double energy(int x, int y) {
        if (x >= width() || x < 0 || y >= height() || y < 0)
            throw new ArrayIndexOutOfBoundsException();
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
            return 1000;
        return Math.sqrt(Math.pow(Rx(x,y),2) + Math.pow(Gx(x,y),2) + Math.pow(Bx(x,y),2) +
                Math.pow(Ry(x,y),2) + Math.pow(Gy(x,y),2) + Math.pow(By(x,y),2) );

    }

    private int Rx(int x, int y){
        return new Color(current[x+1][y]).getRed() -  new Color(current[x-1][y]).getRed();
    }

    private int Gx(int x, int y){
        return new Color(current[x+1][y]).getGreen() -  new Color(current[x-1][y]).getGreen();
    }

    private int Bx(int x, int y){
        return new Color(current[x+1][y]).getBlue() -  new Color(current[x-1][y]).getBlue();
    }

    private int Ry(int x, int y){
        return new Color(current[x][y+1]).getRed() -  new Color(current[x][y-1]).getRed();
    }

    private int Gy(int x, int y){
        return new Color(current[x][y+1]).getGreen() -  new Color(current[x][y-1]).getGreen();
    }

    private int By(int x, int y){
        return new Color(current[x][y+1]).getBlue() -  new Color(current[x][y-1]).getBlue();
    }

    /**
     *
     * @return sequence of indices for horizontal seam
     */
    public int[] findVerticalSeam() {
        int[][] vertexTo = new int[width()][height()];
        double[][] distTo = new double[width()][height()];
        MinPQ<Point> queue = new MinPQ<>();
        init(vertexTo, distTo);
        for(int x = 0; x< width(); x++){
            vertexTo[x][0] = 0;
            double energy = energy(x, 0);
            distTo[x][0] = energy;
            queue.insert(new Point(x,0,energy));
        }
        Point p = null;
        while(!queue.isEmpty()){
            p = queue.delMin();
            if(p.y == height() - 2 || height() == 1)
                break;
            if(p.x > 0){
                relax(p.x-1,p.y+1,p, vertexTo, distTo, queue);
            }
            relax(p.x,p.y+1,p, vertexTo, distTo, queue);
            if(p.x < width() - 1){
                relax(p.x+1,p.y+1,p, vertexTo, distTo, queue);
            }
        }
        return extractSeam(p,vertexTo);
    }

    private void init(int[][] vertexTo, double[][] distTo) {
        for(int i = 0; i < distTo.length; i ++){
            for(int j = 0; j < distTo[i].length; j++){
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }

    }

    private int[] extractSeam(Point p, int[][] vertexTo){
        int[] seam = new int[height()];
        int x = p.x;
        seam[height() - 1] = x;
        if(height()>=2)
            seam[height() - 2] = x;
        for(int y = height() - 2; y> 0 ; y--){
            x = vertexTo[x][y];
            seam[y-1] = x;
        }
        return seam;
    }

    private void relax(int x, int y, Point from, int[][] vertexTo, double[][] distTo, MinPQ<Point> queue){
        double energy = energy(x, y);
        if(distTo[x][y] > from.dist + energy){
            distTo[x][y] = from.dist + energy;
            vertexTo[x][y] = from.x;
            queue.insert(new Point(x,y,distTo[x][y]));
        }
    }

    public int[] findHorizontalSeam() {
        int[][] vertexTo = new int[width()][height()];
        double[][] distTo = new double[width()][height()];
        MinPQ<Point> queue = new MinPQ<>();
        init(vertexTo, distTo);
        for(int y = 0; y< height(); y++){
            vertexTo[0][y] = 0;
            double energy = energy(0, y);
            distTo[0][y] = energy;
            queue.insert(new Point(0,y,energy));
        }
        Point p = null;
        while(!queue.isEmpty()){
            p = queue.delMin();
            if(p.x == width() - 2 || width() == 1)
                break;
            if(p.y > 0){
                relaxH(p.x+1,p.y-1,p, vertexTo, distTo, queue);
            }
            relaxH(p.x+1,p.y,p, vertexTo, distTo, queue);
            if(p.y < height()-1){
                relaxH(p.x+1,p.y+1,p, vertexTo, distTo, queue);
            }
        }
        return extractHSeam(p, vertexTo);
    }

    private int[] extractHSeam(Point p, int[][] vertexTo){
        int[] seam = new int[width()];
        int y = p.y;
        seam[width() - 1] = y;
        if(width()>=2)
            seam[width() - 2] = y;
        for(int x = width() - 2; x> 0 ; x--){
            y = vertexTo[x][y];
            seam[x-1] = y;
        }
        return seam;
    }

    private void relaxH(int x, int y, Point from, int[][] vertexTo, double[][] distTo, MinPQ<Point> queue){
        double energy = energy(x, y);
        if(distTo[x][y] > from.dist + energy){
            distTo[x][y] = from.dist + energy;
            vertexTo[x][y] = from.y;
            queue.insert(new Point(x,y,distTo[x][y]));
        }
    }


   public  void removeHorizontalSeam(int[] seam){
       if(seam.length != width())
           throw new IllegalArgumentException();
       int previous = -1;
        for (int x = 0; x < width(); x ++){
            if(seam[x] < 0 || seam[x] >= height() || (previous != -1 && Math.abs(previous - seam[x]) > 1))
                throw new IllegalArgumentException();
            previous = seam[x];
            for ( int y = seam[x] + 1; y < height(); y++){
                current[x][y-1] = current[x][y];
            }
        }
       height --;
   }   // remove horizontal seam from current picture
    public    void removeVerticalSeam(int[] seam){
        if(seam.length != height())
            throw new IllegalArgumentException();
        int previous = -1;
        for (int y = 0; y < height(); y ++){
            if(seam[y] < 0 || seam[y] >= width() || (previous != -1 && Math.abs(previous - seam[y]) > 1))
                throw new IllegalArgumentException();
            previous = seam[y];
            for ( int x = seam[y] + 1; x < width(); x++){
                current[x-1][y] = current[x][y];
            }
        }
        width --;
    }     // remove vertical seam from current picture

}
