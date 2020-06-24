/*
 * Michael Ventoso <MichaelVentoso@Gmail.com>
 * Submitted 2 November 2017
 * Revised 24 June 2020
 * CMSC 201 Data Structures
 * Lab 7 Stack2
 *
 * Stack 2 functions nearly identically to a stack, with the addition of a sneakPeek method
 * which returns the item second from the top without removing any items from the stack.
 * To make the implementation of sneakPeek easier, Stack2 utilizes a linked list of Nodes as the underlying data structure.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.*;

public class Stack2<Item> implements Iterable<Item> {
    private Node<Item> first;     // top Node of the stack
    private int n;                // store number of items on stack

    // Helper linked list class
    // Each node contains one item and the link to the next node
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // Create empty stack
    public Stack2() {
        first = null;
        n = 0;
    }

    // Returns true if empty, false if not empty
    public boolean isEmpty() {
        return first == null;
    }

    // Returns number of items on the stack
    public int size() {
        return n;
    }

    // Adds an item to the stack
    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }
    /*
    Removes an item from the top of the stack
    Item returned is the last item pushed
    Throws exception if stack is empty
    */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        return item;                   // return the saved item
    }

    /*
    Returns item from top of the stack
    Does not remove item from stack
    Throws exception if stack is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    /*
    Returns item second from top of stack
    Does not remove any items from stack
     */
    public Item sneakPeek(){
        if (this.size() <= 1) throw new NoSuchElementException("Stack underflow");
        return this.first.next.item;
    }

    //Returns a string to visualize the contents of the stack
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    /*
    Returns an iterator to this stack
    Iterates in a last-in-first-out fashion
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove()
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
       /*
       Use for Unit Testing!
        */
       StdOut.println("Use Stack2 Main for Unit Testing!");
    }
}
