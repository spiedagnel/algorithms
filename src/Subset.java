import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by samue_000 on 06/02/2016.
 */
public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String[] strings = StdIn.readAllStrings();
        RandomizedQueue<String> q = new RandomizedQueue<>();
        for (String s : strings){
            q.enqueue(s);
        }
        for (int i = 0; i < k; i ++){
            StdOut.println(q.dequeue());
        }
    }
}
