/**
 * HighScoreList.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that represents a doubly-linked list that stores high scores. Contains
 * a constructor that allows users to set a maximum capacity
 */
package sudoku;


/* Imports */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HighScoreList implements Iterable<HighScoreNode> {

    /* Declare private members */
    private HighScoreNode head, tail;
    private int size, capacity;


    /* Default constructor */
    public HighScoreList() {
        this(10);
    }


    /* Secondary constructor, gives a capacity */
    public HighScoreList(int cap) {
        this.head = this.tail = null;
        this.size = 0;
        this.capacity = cap;
    }


    /**
     * ************** FIXME ********************
     */
    public boolean insertScore(HighScoreNode node) {
        if (this.size == 0) {
            this.head = this.tail = node;
        } else {
            this.tail.setNext(node);
            node.setPrev(this.tail);
            this.tail = node;
        } this.size++;
        if (this.size > this.capacity)
            this.trim();
        return true;
    }


    /**
     * Removes the last high score from the list. Invoked when one extra high
     * score over the list capacity is added.
     */
    private void trim() {
        this.tail = this.tail.getPrev();
        this.tail.setNext(null);
        this.size = this.capacity;
    }


    /**
     * Clears out all the high scores from the list.
     */
    public void clear() {
        this.head = this.tail = null;
        this.size = 0;
    }


    /**
     * Function that returns an iterator, used for iterating by each element in
     * the linked list.
     */
    @Override
    public Iterator iterator() {
        return new HighScoreListIterator();
    }


    /* Internal class that represents the iterator for the list */
    private class HighScoreListIterator implements Iterator {

        /* Declare private members */
        private HighScoreNode curr;

        /* Default constructor */
        public HighScoreListIterator() {
            this.curr = head;
        }

        /**
         * Returns true if there exists another element in the iteration, false
         * if not.
         */
        @Override
        public boolean hasNext() {
            return (this.curr != null);
        }

        /**
         * Returns the next element in the iteration, or throws an exception
         * if no more elements exist in the iteration.
         */
        @Override
        public HighScoreNode next() throws NoSuchElementException {
            if (this.curr == null)
                throw new NoSuchElementException("No more elements in iteration.");
            HighScoreNode node = this.curr;
            this.curr = this.curr.getNext();
            return node;
        }
    }

} // End HighScoreList class