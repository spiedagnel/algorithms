/**
 * Created by samuel on 21/02/16.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {

    private MinPQ<Node> pQueue;
    private boolean solvable;
    private Board initial;
    private Collection<Board> solution;


    private class Node implements Comparable{
        private Board b;
        private Node previous;
        private int moves;
        private int priority;

        public Node(Board b, Node previous) {
            this.b = b;
            this.previous = previous;
            this.moves = previous == null?0:previous.moves+1;
            this.priority = this.moves + this.getB().manhattan();
        }

        public Board getB() {
            return b;
        }

        public Node getPrevious() {
            return previous;
        }


        @Override
        public int compareTo(Object o) {
            Node n2 = (Node)o;
            return (this.priority) - (n2.priority);
        }
    }
    /**
     *
     * @param initial find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        this.initial = initial;
        pQueue = new MinPQ<>();
        Node x = new Node(initial, null);
        pQueue.insert(x);
        pQueue.insert(new Node(initial.twin(),null));
        if(initial.isGoal()){
            this.solution = new ArrayList<>();
            this.solution.add(initial);
            this.solvable = true;
        } else
            solve();
    }

    private void solve(){
        Node n = null;
        while(!pQueue.isEmpty()){
            n = processQueue(pQueue);
            if (n != null) {
                break;
            }

        }
        ArrayList<Board> list = new ArrayList<>();
        while (n!=null) {
            list.add(n.getB());
            n = n.previous;
        }
        Collections.reverse(list);
        if(list.get(0).equals(this.initial)){
            solvable = true;
            this.solution = list;
        }
    }

    private Node processQueue(MinPQ<Node> queue) {
        Node node = queue.delMin();

        Iterable<Board> neighbors = node.getB().neighbors();
        for (Board b : neighbors){
            if (b.isGoal()){
                return new Node(b,node);

            } else {
                if(node.getPrevious() == null || !b.equals(node.getPrevious().getB()))
                    queue.insert(new Node(b,node));
            }
        }
        return null;
    }

    /**
     *
     * @return is the initial board solvable?
     */
    public boolean isSolvable(){
        return this.solvable;
    }

    /**
     *
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if(solvable)
            return solution.size()-1;
        return -1;
    }

    /**
     *
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        ArrayList<Board> list = new ArrayList<>();
        if (solvable){
            list.addAll(solution);
            return list;
        }
        return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
