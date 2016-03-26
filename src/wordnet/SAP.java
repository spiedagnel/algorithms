package wordnet;

import edu.princeton.cs.algs4.*;
import java.util.HashMap;
import java.util.Map;

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
            HashMap<Integer, Integer> visitedV = new HashMap<>();
            HashMap<Integer, Integer> visitedW = new HashMap<>();
            Queue<V> qV = new Queue<>();
            Queue<V> qW = new Queue<>();
            qV.enqueue(new V(v, 0));
            qW.enqueue(new V(w, 0));
            visitedV.put(v,0);
            visitedW.put(w,0);
            V ancestor = null;
            while (!qV.isEmpty() || !qW.isEmpty()) {
                if(!qV.isEmpty())
                    ancestor = processQueue(qV, ancestor,visitedV,visitedW);

                if(!qW.isEmpty())
                    ancestor = processQueue(qW, ancestor,visitedW,visitedV);

            }
            solution.put(key, ancestor);
            solution.put(rkey, ancestor);
            return ancestor;
        }
    }

    private V processQueue(Queue<V> qV, V ancestor, Map<Integer,Integer> currentVisited,Map<Integer,Integer> targetVisited ) {
        V currentNode = qV.dequeue();

        if (targetVisited.containsKey(currentNode.vertex)) {
            V value = new V(currentNode.vertex, currentNode.distance + targetVisited.get(currentNode.vertex));
            if (ancestor == null || value.distance < ancestor.distance)
            ancestor = value;
        }
            //currentVisited.put(currentNode.vertex, currentNode.distance);
            for (int adj : dg.adj(currentNode.vertex)) {
                if(!currentVisited.containsKey(adj) || currentVisited.get(adj) > currentNode.distance) {
                    qV.enqueue(new V(adj, currentNode.distance + 1));
                    currentVisited.put(adj, currentNode.distance + 1);
                }
            }

        return ancestor;
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


    private V findAncestor(Iterable<Integer> V, Iterable<Integer> Y){
        if(V == null || Y == null)
            throw new NullPointerException();
        V min = null;
        for (int v : V){
            for ( int y : Y){
                V ancestor = findAncestor(v,y);
                if ((ancestor != null) && ((min == null) || ( ancestor.distance < min.distance))){
                    min = ancestor;
                }
            }
        }
        return min;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){

        V ancestor = findAncestor(v, w);
        if(ancestor == null){
            return -1;
        } else {
            return ancestor.distance;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        V ancestor = findAncestor(v, w);
        if(ancestor == null){
            return -1;
        } else {
            return ancestor.vertex;
        }
    }

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
