package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by samue_000 on 30/01/2016.
 */
public class Percolation {

    private WeightedQuickUnionUF wquf;
    private boolean[] states;
    private boolean[] topConnected;
    private boolean[] bottomConnected;

    private boolean percolated;
    private int N;

    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        this.N = N;
        if (N <= 0)
            throw new IllegalArgumentException();
        wquf = new WeightedQuickUnionUF(N*N+2);
        states = new boolean[N*N+1];
        topConnected = new boolean[N*N+1];
        bottomConnected = new boolean[N*N+1];

        for (int i = 0; i <= N*N; i++) {
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
        validateEntry(i, j);
        int idx = getIndex(i, j);
        boolean connectedToTop = false;
        boolean connectedToBottom = false;
        if (!isOpen(i, j)) {
            states[idx] = true;
            int indexNeighbor = getIndex(i - 1, j);
            if (i > 1 && states[indexNeighbor]) {
                int rootToConnect = wquf.find(indexNeighbor);
                connectedToTop = connectedToTop || topConnected[rootToConnect];
                connectedToBottom = connectedToBottom || bottomConnected[rootToConnect];
                wquf.union(idx, indexNeighbor);
            } else if (i == 1) {
                wquf.union(idx, 0);
                connectedToTop = true;
            }
            indexNeighbor = getIndex(i, j - 1);
            if (j > 1 && states[indexNeighbor]) {
                int rootToConnect = wquf.find(indexNeighbor);
                connectedToTop = connectedToTop || topConnected[rootToConnect];
                connectedToBottom = connectedToBottom || bottomConnected[rootToConnect];
                wquf.union(idx, indexNeighbor);
            }
            indexNeighbor = getIndex(i, j + 1);
            if (j < N && states[indexNeighbor]) {
                int rootToConnect = wquf.find(indexNeighbor);
                connectedToTop = connectedToTop || topConnected[rootToConnect];
                connectedToBottom = connectedToBottom || bottomConnected[rootToConnect];
                wquf.union(idx, indexNeighbor);

            }
            indexNeighbor = getIndex(i + 1, j);
            if (i < N && states[indexNeighbor]) {
                int rootToConnect = wquf.find(indexNeighbor);
                connectedToTop = connectedToTop || topConnected[rootToConnect];
                connectedToBottom = connectedToBottom || bottomConnected[rootToConnect];
                wquf.union(idx, indexNeighbor);
            } else if (i == N) {
                connectedToBottom = true;
            }
            idx = wquf.find(idx);
            topConnected[idx] = connectedToTop;
            bottomConnected[idx] = connectedToBottom;
            if (connectedToBottom && connectedToTop)
                percolated = true;
        }
    }
    /**
     *
     * @param i row index
     * @param j column index
     * @return true if site open else fase
     */
    public boolean isOpen(int i, int j) {
        validateEntry(i, j);
        return states[getIndex(i, j)];
    }

    private int getIndex(int i, int j) {
        return (i-1)*N+j;
    }

    private void validateEntry(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N)
            throw new IndexOutOfBoundsException();
    }
    /**
    * @param i row index
    * @param j column index
    * @return true if site open else fase
     */
    public boolean isFull(int i, int j) {
        validateEntry(i, j);
        int index = getIndex(i, j);
        return topConnected[wquf.find(index)];
    }

    public boolean percolates() {
        return percolated;
    }

    public static void main(String[] args) {

    }
}
