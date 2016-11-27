/**
 * HighScoreNode.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.text.SimpleDateFormat;
import java.util.Date;

public class HighScoreNode {

    /* Declare private members */
    private final int score;
    private final String date;
    private HighScoreNode prev, next;

    /* Default constructor */
    public HighScoreNode(int score) {
        this.score = score;
        this.date = new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
        this.prev = this.next = null;
    }


    /***/
    public int getScore() {
        return this.score;
    }


    /***/
    public String getDate() {
        return this.date;
    }


    /***/
    public HighScoreNode getPrev() {
        return this.prev;
    }


    /***/
    public void setPrev(HighScoreNode n) {
        this.prev = n;
    }


    /***/
    public HighScoreNode getNext() {
        return this.next;
    }


    /***/
    public void setNext(HighScoreNode n) {
        this.next = n;
    }

} // End HighScoreNode class