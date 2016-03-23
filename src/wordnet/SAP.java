package wordnet;

import edu.princeton.cs.algs4.*;
import java.util.HashMap;

/**
 * Created by samuel on 23/03/16.
 */
public class SAP {
    private Digraph dg;
    private HashMap<V,V> solution = new HashMap<>();
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G == null){
            throw new NullPointerException();
        }
        dg = G;
    }

    private class V{
        int distance;
        int vertex;

        public V(int vertex, int distance) {
            this.distance = distance;
            this.vertex = vertex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            V v = (V) o;

            if (distance != v.distance) return false;
            return vertex == v.vertex;

        }

        @Override
        public int hashCode() {
            int result = distance;
            result = 31 * result + vertex;
            return result;
        }

        @Override
        public String toString() {
            return "V{" +
                    "distance=" + distance +
                    ", vertex=" + vertex +
                    '}';
        }
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        V ancestor = findAncestor(v, w);
        if(ancestor == null){
            return -1;
        } else {
            return ancestor.distance;
        }
    }

    private V findAncestor(int v, int w){
        V key = new V(v, w);
        V rkey = new V(w, v);
        if (solution.containsKey(key)){
            return solution.get(key);
        } else {
            HashMap<Integer, Integer> visited = new HashMap<>();
            Queue<V> q = new Queue<>();
            q.enqueue(new V(v, 0));
            q.enqueue(new V(w, 0));
            while (!q.isEmpty()) {
                V currentNode = q.dequeue();
                if (visited.containsKey(currentNode.vertex)) {
                    V value = new V(currentNode.vertex, currentNode.distance + visited.get(currentNode.vertex));
                    solution.put(key, value);
                    solution.put(rkey, value);
                    return value;
                } else {
                    visited.put(currentNode.vertex, currentNode.distance);
                    for (int adj : dg.adj(currentNode.vertex)) {
                        q.enqueue(new V(adj, currentNode.distance + 1));
                    }
                }
            }
            solution.put(key, null);
            solution.put(rkey, null);
            return null;
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        V ancestor = findAncestor(v, w);
        if(ancestor == null){
            return -1;
        } else {
            return ancestor.vertex;
        }
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   // public int length(Iterable<Integer> v, Iterable<Integer> w)

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
  //  public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

    // do unit testing of this class
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
