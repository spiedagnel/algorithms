import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by samue_000 on 06/02/2016.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    private class Node<Item> {

        private Item item;
        private Node next;
        private Node previous;

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
            this.previous = null;
        }

        public Item getItem() {
            return item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator(Node current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(current == null)
                throw new NoSuchElementException();
            Item item = (Item)current.item;
            current = current.next;
            return item;
        }

        public void remove()      { throw new UnsupportedOperationException();  }
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node n = new Node(item, first);
        if(isEmpty()){
            last = n;
        } else {
            first.previous = n;
        }
        first = n;
        size ++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node n = new Node(item, null);
        if(isEmpty()) {
            first = n;
        } else {
            last.next = n;
        }
        n.previous = last;
        last = n;
        size ++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item n = first.item;
        first = first.next;
        if (first != null)
            first.previous = null;
        else
            last = null;
        size --;
        return n;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item n = last.item;
        last = last.previous;
        if(last != null)
            last.next = null;
        else
            first = null;
        size --;
        return n;

    }

    public Iterator<Item> iterator(){
        return new DequeIterator(first);
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(0);
        deque.removeLast();
        deque.addLast(2);
        deque.size();
        deque.removeFirst();
        System.out.println(deque.isEmpty());
        for (int i : deque)
            System.out.printf(i+"");


    }
}
