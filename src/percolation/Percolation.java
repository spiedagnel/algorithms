package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by samue_000 on 30/01/2016.
 */
public class Percolation {

    private WeightedQuickUnionUF wquf;
    private boolean[] states;
    private int N;

    public Percolation(int N){
        // create N-by-N grid, with all sites blocked
        this.N = N;
        if(N<=0)
            throw new IllegalArgumentException();
        wquf = new WeightedQuickUnionUF(N*N+2);
        states = new boolean[N*N+1];
        for(int i=0; i<=N*N; i++){
            states[i] = false;
        }
    }

    /**
     *
     * @param i row index
     * @param j column index
     * @return void
     *
     * open site if not open already
     */
    public void open(int i, int j) {
        validateEntry(i,j);
        int idx = getIndex(i,j);
        if(!isOpen(i,j)){
            states[idx] = true;
            if(i>1 && states[getIndex(i-1,j)])
                wquf.union(idx,getIndex(i-1,j));
            else if (i==1){
                wquf.union(idx,0);
            }
            if(j>1 && states[getIndex(i,j-1)])
                wquf.union(idx,getIndex(i,j-1));
            if(i<N && states[getIndex(i+1,j)])
                wquf.union(idx,getIndex(i+1,j));
            else if (i==N){
                wquf.union(idx,N*N+1);
            }
            if(j<N && states[getIndex(i,j+1)])
                wquf.union(idx,getIndex(i,j+1));
        }
    }
    /**
     *
     * @param i row index
     * @param j column index
     * @return true if site open else fase
     */
    public boolean isOpen(int i, int j) {
        validateEntry(i,j);
        return states[getIndex(i,j)];
    }

    private int getIndex(int i, int j){
        return (i-1)*N+j;
    }

    private void validateEntry(int i, int j) throws IndexOutOfBoundsException{
        if(i<1 || j<1 || i>N || j>N)
            throw new IndexOutOfBoundsException();
    }
    /**
    * @param i row index
    * @param j column index
    * @return true if site open else fase
     */
    public boolean isFull(int i, int j) {
        validateEntry(i,j);
        return wquf.connected(getIndex(i,j),0);
    }

    public boolean percolates() {
        return wquf.connected(0,N*N+1);
    }

    public static void main(String[] args) {

    }
}
