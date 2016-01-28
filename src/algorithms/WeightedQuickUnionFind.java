package algorithms;

/**
 * Created by samue_000 on 28/01/2016.
 */
public class WeightedQuickUnionFind {

    private final int length;
    private int[] parents;
    private int[] sizes;

    public WeightedQuickUnionFind(int length){
        this.parents = new int[length];
        this.sizes = new int[length];
        for(int i =0; i < length; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }
        this.length = length;
    }

    public void union(int p, int q){
        p = root(p);
        q = root(q);
        if(p == q)
            return;
        if(sizes[p]<sizes[q]){
            parents[p] = q;
            sizes[q] += sizes[p];
        }else{
            parents[q] = p;
            sizes[p] += sizes[q];
        }
    }

    public boolean find(int p, int q){
        return root(p) == root(q);
    }


    private int root(int p){
        while(parents[p]!=p)
            p = parents[p];
        return p;
    }

    public void print(){
        for(int i : parents)
            System.out.print(i+" ");
        System.out.println();
    }

    public static void main(String[] args) {
        WeightedQuickUnionFind wuf = new WeightedQuickUnionFind(10);
        wuf.union(7,3);
        wuf.print();
        wuf.union(0, 4);
        wuf.print();
        wuf.union(1, 6);
        wuf.print();
        wuf.union(0, 6);
        wuf.print();
        wuf.union(9, 7);
        wuf.print();
        wuf.union(3, 5);
        wuf.print();
        wuf.union(7, 8);
        wuf.print();
        wuf.union(2, 8);
        wuf.print();
        wuf.union(3,4);
        wuf.print();
    }
}
