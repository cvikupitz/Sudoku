/**
 * HighScoreNode.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that represents a node to be stored inside the high score list class used
 * to store the user's highest scores. The node class can hold a score (int) and
 * will construct and store a date (String) as well. Contnains pointers to a
 * previous and next node for the doubly-linked list structure.
 */
package sudoku;


/* Imports */
import java.text.SimpleDateFormat;
import java.util.Date;

public class HighScoreNode {

    /* Declare private members */
    private final int score;
    private String date;
    private HighScoreNode prev, next;


    /* Default constructor */
    public HighScoreNode(int score) {
        this.score = score;
        this.date = new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
        this.prev = this.next = null;
    }


    /**
     * Returns the score stored inside the node.
     *
     * @return The score stored in the node.
     */
    public int getScore() {
        return this.score;
    }


    /**
     * Returns the date in a string format that was formed in the constructor
     * and associated with the stored score.
     *
     * @return A string representing the date the score was added.
     */
    public String getDate() {
        return this.date;
    }


    /**
     * Sets the stored date to the specified date string.
     *
     * @param date The new date string to store.
     */
    public void setDate(String date) {
        this.date = date;
    }


    /**
     * Returns the previously linked node.
     *
     * @return The node that is linked previous to this node.
     */
    public HighScoreNode getPrev() {
        return this.prev;
    }


    /**
     * Sets this node's previous link to the specified node.
     *
     * @param n The node to link this node to previously.
     */
    public void setPrev(HighScoreNode n) {
        this.prev = n;
    }


    /**
     * Returns the next linked node.
     *
     * @return The node that is linked next to this node.
     */
    public HighScoreNode getNext() {
        return this.next;
    }


    /**
     * Sets this node's next link to the specified node.
     *
     * @param n The node to link this node to next.
     */
    public void setNext(HighScoreNode n) {
        this.next = n;
    }

} // End HighScoreNode class