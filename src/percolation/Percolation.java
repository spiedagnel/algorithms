package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by samue_000 on 30/01/2016.
 */
public class Percolation {

    private WeightedQuickUnionUF wquf;
    private boolean[] states;
    private boolean[] emptyFull;
    private boolean[] bottom;
    private boolean openedBottom;
    private boolean percolated;
    private int N;

    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        this.N = N;
        if (N <= 0)
            throw new IllegalArgumentException();
        wquf = new WeightedQuickUnionUF(N*N+2);
        states = new boolean[N*N+1];
        emptyFull = new boolean[N*N+1];
        bottom = new boolean[N];
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
        boolean flowing = false;
        if (!isOpen(i, j)) {
            states[idx] = true;
            if (i > 1 && states[getIndex(i-1, j)]) {
                wquf.union(idx, getIndex(i - 1, j));
                flowing = emptyFull[getIndex(i - 1, j)];
            } else if (i == 1) {
                wquf.union(idx, 0);
                emptyFull[idx] = true;
                flowing = flowing || true;
            }
            if (j > 1 && states[getIndex(i, j-1)]) {
                wquf.union(idx, getIndex(i, j - 1));
                flowing = flowing || emptyFull[getIndex(i, j - 1)];
            }
            if (j < N && states[getIndex(i, j+1)]) {
                wquf.union(idx, getIndex(i, j + 1));
                flowing = flowing || emptyFull[getIndex(i, j + 1)];
            }
            if (i < N && states[getIndex(i+1, j)]) {
                wquf.union(idx, getIndex(i + 1, j));
                flowing = flowing || emptyFull[getIndex(i + 1, j)];
            } else if (i == N) {
                openedBottom = true;
                bottom[j-1] = true;
                int k = j-2;
                while (k >= 0 && bottom[k]) {
                    bottom[k] = false;
                    k -= 1;
                }
                k = j;
                while (k < N && bottom[k]) {
                    bottom[k] = false;
                    k += 1;
                }
            }
            if(flowing) {
                for (int k = 0; k < N; k++) {
                    if (bottom[k] && wquf.connected(idx, getIndex(N, k + 1))) {
                        percolated = true;
                    }
                }
            }
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
        if (emptyFull[index]) {
            return true;
        } else {
            if (wquf.connected(index, 0)) {
                emptyFull[index] = true;
                return true;
            }
        }
        return false;
    }

    public boolean percolates() {
        if (percolated) {
            return true;
        }
        if (!openedBottom) {
            return false;
        }
//        for (int i = 0; i < N; i++) {
//            if (bottom[i] && wquf.connected(0, getIndex(N, i+1))) {
//                percolated = true;
//                return true;
//            }
//        }
        return false;
    }

    public static void main(String[] args) {

    }
}
