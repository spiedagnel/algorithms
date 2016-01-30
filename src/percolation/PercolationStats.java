package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by samue_000 on 30/01/2016.
 */
public class PercolationStats {

    private double[] results;

    /**
     *
     * @param N grid size (N-by-N)
     * @param T number of experiments
     *
     * performs T independent experiments on an N-by-N grid
     *          throws a java.lang.IllegalArgumentException if either N ? 0 or T ? 0.
     */
    public PercolationStats(int N, int T)   {
        if(N<=0 || T<=0)
            throw new IllegalArgumentException();
        results = new double[T];
        for(int i=0;i<T;i++){
            results[i] = (double)runOneExperiment(N)/(N*N);
        }

    }

    private int runOneExperiment(int N) {
        Percolation p = new Percolation(N);
        int count = 0;
        while(!p.percolates()){
            int i = StdRandom.uniform(N)+1;
            int j = StdRandom.uniform(N)+1;
            if(!p.isOpen(i,j)){
                p.open(i,j);
                count+=1;
            }
        }
        return count;
    }

    /**
     *
     * @return ample mean of percolation threshold
     */
    public double mean()    {
        return StdStats.mean(results);
    }

    /**
     *
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     *
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(results.length);
    }

    /**
     *
     * @return high endpoint of 95% confidence interval
     */

    public double confidenceHi(){
        return mean() + 1.96*stddev()/Math.sqrt(results.length);
    }

    public static void main(String[] args) {
        Integer n = Integer.parseInt(args[0]);
        Integer t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean                    = "+ps.mean());
        System.out.println("stdev                   = "+ps.stddev());
        System.out.println("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());
    }

}
