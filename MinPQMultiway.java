/*
 * Michael Ventoso <MichaelVentoso@Gmail.com>
 * Submitted 2 November 2017
 * Revised 24 June 2020
 * CMSC 201 Data Structures
 * Lab 7 Stack2
 *
 * MinPQMultiway is an implementation of a priority queue where the minimum value is easily accessible.
 * It differs from normal binary implementations in that the user can specify the number of child nodes per parent.
 * The underlying data structure is an array of keys, and there are several different constructors to allow flexibility
 * for the client.
 * One fundamental aspect of the MinPQMultiway is that the smallest key in the queue can be accessed in constant time;
 * the heavy lifting is done when inserting a new key or removing the minimum key.
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.*;

public class MinPQMultiway<Key> implements Iterable<Key> {
    private Key[] pq;                    // main data structure for storing items
    private int n;                       // store current size of pq
    private Comparator<Key> comparator;  // comparator is optional
    private int D;                       // number of child nodes per parent


    public MinPQMultiway(int initCapacity, int childNum) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
        D = childNum;
    }

    public MinPQMultiway() {
        this(1,2);
    }

    public MinPQMultiway(int initCapacity, Comparator<Key> comparator, int childNum) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
        D = childNum;
    }

    public MinPQMultiway(Comparator<Key> comparator) {
        this(1, comparator, 2);
    }

    public MinPQMultiway(int initCapacity, Comparator<Key> comparator) {
        this(initCapacity,  comparator, 2);
    }

    public MinPQMultiway(Comparator<Key> comparator, int childNum) {
        this(1,  comparator, childNum);
    }

    // Creates MinPQMultiway from an existing array of keys
    public MinPQMultiway(Key[] keys, int childNum) {
        D = childNum;
        n = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++)
            pq[i+1] = keys[i];
        for (int k = n/2; k >= 1; k--)
            sink(k);
    }

    // Returns True if there are no keys in the queue, and False if there is at least one
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of keys on the queue
    public int size() {
        return n;
    }

    // Returns the smallest key *in constant time*
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // Doubles the size of the underlying array if needed, and does not slow the runtime significantly in the long run
    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

   // Adds a key and swims it to ensure the resulting PQ is a heap.
    public void insert(Key x) {
        // double size of array if necessary
        if (n == pq.length - 1) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
    }

    // Removes the smallest key
    // The swim is to ensure the resulting PQ is still a heap.
    public Key delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key min = pq[1];
        exchange(1, n--);
        sink(1);
        pq[n+1] = null;     // to avoid loitering and help with garbage collection
        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
        return min;
    }

    /*
    Helper to insert
    Gets newly inserted key into the right place in the heap
     */
    private void swim(int k) {
        while (k > 1 && greater(parent(k), k)) {
            exchange(k, parent(k));
            k = parent(k);
        }
    }

    /*
    Helper to delMin
    Keeps the tree a heap after deleting the minimum key from the top by checking all of the parents children
    before the exchange
     */
    private void sink(int k) {
        while ((child(k) <= n) ) {
            int j = child(k);
            int minIndex = j;

            for (int x = j; x < j + this.D; x++){
                if (x>n){
                    break;
                }
                if (greater(minIndex,x)){
                    minIndex = x;
                }
            }
            if (greater(minIndex,k)){
                break;
            }
            exchange(k, minIndex);
            k = minIndex;
        }
    }

    // Helper to Swim to perform calculation based on how many children there are per parent
    private int parent(int child){
        return ((child + (this.D - 2)) / this.D);
    }

    // Helper to Sink to perform calculation based on how many children there are per parent
    private int child(int parent){
        return ((this.D * parent) - (this.D - 2));
    }

    /*
    Deal with comparisons of different types
    Use this instead of == for any comparisons in this class to avoid bugs/errors at compile
     */
    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    // Helper that switches two keys in the PQ by switching their positions in the underlying array
    private void exchange(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }


    /*
    Returns an iterator to this MinPQ
    Iterates from smallest to largest key
     */
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        // create a new pq
        private MinPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new MinPQ<Key>(size());
            else                    copy = new MinPQ<Key>(size(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    //Use main for unit testing
    public static void main(String[] args) {
        /*
       Use for Unit Testing!
        */
        StdOut.println("Use MinPQMultiway Main for Unit Testing!");
    }

}