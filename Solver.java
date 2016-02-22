/**
 * Created by samuel on 21/02/16.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {

    private MinPQ<Node> initialQueue;
    private MinPQ<Node> twinQueue;
    private boolean solvable;
    private Node goal;


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
        initialQueue = new MinPQ<>();
        twinQueue = new MinPQ<>();
        Node x = new Node(initial, null);
        initialQueue.insert(x);
        twinQueue.insert(new Node(initial.twin(),null));
        if(initial.isGoal()){
            this.goal = x;
            this.solvable = true;
        } else
            solve();
    }

    private List<Board> visited;

    private void solve(){
        boolean done = false;
        visited = new ArrayList<>();
        while(!done){
            Node n;
            if (!initialQueue.isEmpty()) {
                n = processQueue(initialQueue);
                if (n != null) {
                    this.goal = n;
                    this.solvable = true;
                    break;
                }
            }
            n = processQueue(twinQueue);
            if (n!= null){
                this.solvable = false;
                done = true;
            }
        }
    }

    private Node processQueue(MinPQ<Node> queue) {
        Node node = queue.delMin();

        Iterable<Board> neighbors = node.getB().neighbors();
        for (Board b : neighbors){
            if (b.isGoal()){
                return new Node(b,node);

            } else {

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
        if(goal != null)
            return goal.moves;
        return -1;
    }

    /**
     *
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        ArrayList<Board> list = new ArrayList<>();
        if (goal != null){
            Node n = goal;
            while (n!=null) {
                list.add(n.getB());
                n = n.previous;
            }
            Collections.reverse(list);
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
