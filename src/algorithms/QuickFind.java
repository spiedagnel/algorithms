package algorithms;

/**
 * Created by samue_000 on 28/01/2016.
 */
public class QuickFind {

    private int[] id;

    public QuickFind(int length) {
        id = new int[length];
        for(int i=0; i<length; i++){
            id[i] = i;
        }
    }

    public boolean  connected(int p, int q){
        return id[p] == id[q];
    }

    public void union(int p,int q){
        int pid = id[p];
        int qid = id[q];
        for(int i = 0; i <id.length;i++){
            if(id[i]==pid)
                id[i] = qid;
        }

    }

    public void print(){
        for(int i : id)
            System.out.print(i+" ");
        System.out.println();

    }

    public static void main(String[] args) {
        QuickFind qf = new QuickFind(10);
        qf.union(3,6);
        qf.print();
        qf.union(3,1);
        qf.print();
        qf.union(4,9);
        qf.print();
        qf.union(7,8);
        qf.print();
        qf.union(3,0);
        qf.print();
        qf.union(8,3);
        qf.print();
    }
}
