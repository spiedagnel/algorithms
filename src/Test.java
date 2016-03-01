import edu.princeton.cs.algs4.MaxPQ;

/**
 * Created by samuel on 14/02/16.
 */
public class Test {

    public static void main(String[] args) {
        // 98 92 86 47 50 40 56 25 45 23
        MaxPQ<Integer> q = new MaxPQ();
        q.insert(98);
        q.insert(92);
        q.insert(86);
        q.insert(47);
        q.insert(50);
        q.insert(40);
        q.insert(56);
        q.insert(25);
        q.insert(45);
        q.insert(23);

        q.insert(36);
        q.insert(11);
        q.insert(17);
    }
}
