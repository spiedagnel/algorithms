import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by samue_000 on 06/02/2016.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int first, last;
    private int N;

    public RandomizedQueue() {
        q = (Item[])new Object[2];
        first = 0;
        last = 0;
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == q.length) resize(N * 2);
        q[last++] = item;
        N++;
    }

    private void resize(int newSize) {
        Item[] newQ = (Item[])new Object[newSize];
        for (int i = 0; i < N; i++) {
            newQ[i] = q[(first + i) % q.length];
        }
        q = newQ;
        first = 0;
        last  = N;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int rnd = StdRandom.uniform(N);
        Item item = q[rnd];
        q[rnd] = q[--last];
        q[last] = null;                            // to avoid loitering
        N--;
        if (N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return q[StdRandom.uniform(N)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }
        private Item[] copy;

        public RandomizedIterator() {
            copy = (Item[])new Object[N];
            for (int k = 0; k < N ; k ++){
                copy[k] = q[k];
            }
            StdRandom.shuffle(copy);
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy[i++];
        }
    }

    public static void main(String[] args)  {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        Iterator<Integer> it = q.iterator();
        while (it.next()!=3){}
    }
}
