import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by samuel on 16/02/16.
 */
public class Board {

    /**
     *
     * @param blocks
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */

    private int[][] blocks;
    private int N;
    private int i0, j0;

    public Board(int[][] blocks){
        this.N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0){
                    i0 = i;
                    j0 = j;
                }
            }
        }
    }

    public int dimension() {
        return N;
    }

    /**
     *
     * @return number of blocks out of place
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != i*N + j+1)
                    count++;
            }
        }
        return count;
    }

    /**
     *
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                count += Math.abs(i-this.blocks[i][j]/N);
                count += Math.abs(j-this.blocks[i][j]%N);
            }
        }
        return count;

    }

    public boolean isGoal(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != i*N + j+1)
                    return false;
            }
        }
        return true;
    }

    /**
     *
     * @return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        int[][] twin = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                twin[i][j] = this.blocks[i][j];
            }
        }
        int swap;
        int iswap = (i0 == 0)?1:0;
        swap = twin[iswap][0];
        twin[iswap][0] = twin[iswap][1];
        twin[iswap][1] = swap;
        return new Board(twin);
    }

    /**
     *
     * @param y
     * @return does this board equal y?
     */
    public boolean equals(Object y){
        if (y instanceof Board){
            Board boardY = (Board)y;
            if(this.dimension() == boardY.dimension()){
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (this.blocks[i][j] != boardY.blocks[i][j])
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors(){
        ArrayList<Board> list = new ArrayList<>();
        Board b = null;
        if (i0 > 0){
            b = new Board(this.blocks);
            b.blocks[i0][j0] = this.blocks[i0-1][j0];
            b.blocks[i0-1][j0] = 0;
            b.i0 = i0-1;

            list.add(b);
        }
        if (i0 < N){
            b = new Board(this.blocks);
            b.blocks[i0][j0] = this.blocks[i0+1][j0];
            b.blocks[i0+1][j0] = 0;
            b.i0 = i0+1;
            list.add(b);
        }
        if (j0 > 0){
            b = new Board(this.blocks);
            b.blocks[i0][j0] = this.blocks[i0][j0-1];
            b.blocks[i0][j0-1] = 0;
            b.j0 = j0-1;
            list.add(b);
        }
        if (j0 < N){
            b = new Board(this.blocks);
            b.blocks[i0][j0] = this.blocks[i0][j0+1];
            b.blocks[i0][j0+1] = 0;
            b.j0 = j0+1;
            list.add(b);
        }
        return list;
    }



    /**
     *
     * @return string representation of this board
     */
    public String toString() {
        StringBuffer sbf = new StringBuffer();
        sbf.append(N).append("\n");
        for(int i =0 ; i < N; i++){
            for (int j =0 ; j < N; j++){
                sbf.append(blocks[i][j]+" ");
            }
            sbf.append("\n");
        }
        return sbf.toString();
    }

    public static void main(String[] args) {
        int[] [] i = {{0,1,4},{2,3,5},{6,7,8}};
        Board b = new Board(i);
        StdOut.println(b);
        StdOut.println(b.isGoal());
        StdOut.println(b.twin());
        for ( Board n : b.neighbors())
            StdOut.println(n);

        int[] [] j = {{0,1},{2,3}};
        Board a = new Board(j);
        StdOut.println(a);
        StdOut.println(a.hamming());
        StdOut.println(a.manhattan());
    }
}
