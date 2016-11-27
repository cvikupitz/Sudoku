/**
 * HighScoreList.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class HighScoreList implements Iterable {

    private Node head, tail;
    private int size, capacity;


    /* Internal class to represent a node in the list, stores the score and date. */
    private class Node {

        int score;
        String date;
        Node prev, next;

        Node(int score) {
            this.score = score;
            this.date = new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
            this.next = this.prev = null;
        }

        int getScore() {
            return this.score;
        }
        String getDate() {
            return this.date;
        }
    }


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

        Node node = new Node(score);
        Node curr = this.head;


    }


    private void update() {}


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

        public HighScoreListIterator() {}

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            return null;
        }
    }
}
