/**
 * HighScoreList.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HighScoreList implements Iterable<HighScoreNode> {

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


    /***/
    public boolean insertScore(int score) {
        return true;
    }


    /***/
    private void update() {

    }


    /***/
    public void clear() {
        this.head = this.tail = null;
        this.size = 0;
    }


    /***/
    @Override
    public Iterator iterator() {
        return new HighScoreListIterator();
    }

    private class HighScoreListIterator implements Iterator {

        private HighScoreNode curr;

        public HighScoreListIterator() {
            this.curr = head;
        }

        @Override
        public boolean hasNext() {
            return (this.curr == null);
        }

        @Override
        public HighScoreNode next() throws NoSuchElementException {
            if (this.curr == null)
                throw new NoSuchElementException("No more elements in iteration.");
            HighScoreNode node = this.curr;
            this.curr = this.curr.getNext();
            return node;
        }
    }


    public static void main(String[] args) {
        HighScoreList h = new HighScoreList();

    }
}
