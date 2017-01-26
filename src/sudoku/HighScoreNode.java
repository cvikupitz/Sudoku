/**
 * HighScoreNode.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that represents a node to be stored inside the array lists used
 * to store the user's highest scores. The node class can hold a score (int) and
 * will construct and store a date (String) as well. Also contains a comparator to
 * compare other scores.
 */
package sudoku;


/* Imports */
import java.text.SimpleDateFormat;
import java.util.Date;

public class HighScoreNode implements Comparable<HighScoreNode> {

    /* Declare private members */
    private final int score;
    private String date;


    /* Default constructor */
    public HighScoreNode(int score) {
        this.score = score;
        this.date = new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
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
     * Returns an integer representing the result of comparing two nodes. Returns
     * 0 if the nodes' stored scores are equal, 1 if this node's score is greater
     * than the other's, or -1 if less than the other's.
     *
     * @param other The other node to compare with.
     */
    @Override
    public int compareTo(HighScoreNode other) {
        if (this.getScore() < other.getScore())
            return -1;
        else if (this.getScore() > other.getScore())
            return 1;
        else return 0;
    }

} // End HighScoreNode class