/**
 * BestTimes.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains static array lists that stores the user's top 10 times in each
 * difficulty, including novice, easy, medium, hard, and expert puzzles. Also
 * contains a method for inserting scores into the lists that will accept or
 * decline the score as a top-10 score, and resort the list if needed. Also
 * contains a method for getting a string representation of the date the score
 * was achieved, used for displaying purposes.
 */
package sudoku;


/* Imports */
import java.util.ArrayList;
import java.util.Collections;

public class BestTimes {

    /* Static variable to temporarily store a time for resuming/saving a game */
    protected static int time = 0;

    /* Linked list to store the best novice puzzle times */
    protected static ArrayList<HighScoreNode> novice = new ArrayList<HighScoreNode>();

    /* Linked list to store the best easy puzzle times */
    protected static ArrayList<HighScoreNode> easy = new ArrayList<HighScoreNode>();

    /* Linked list to store the best medium puzzle times */
    protected static ArrayList<HighScoreNode> medium = new ArrayList<HighScoreNode>();

    /* Linked list to store the best hard puzzle times */
    protected static ArrayList<HighScoreNode> hard = new ArrayList<HighScoreNode>();

    /* Linked list to store the best expert puzzle times */
    protected static ArrayList<HighScoreNode> expert = new ArrayList<HighScoreNode>();


    /**
     * Inserts the specified time into the corresponding list storing the best
     * times associated with the specified difficulty. Returns true if the
     * time given is a top 10 time, false if not.
     *
     * @param time The time to insert into the list.
     * @param difficulty The difficulty of the puzzle the time was extracted
     * from (1 = novice, 2 = easy, 3 = medium, 4 = hard, 5+ = expert).
     * @return True if the inserted time is a new best time, false if not.
     */
    protected static boolean insertBestTime(int time, int difficulty) {

        HighScoreNode node = new HighScoreNode(time);
        switch (difficulty) {
            case 1:     /* Insert into novice difficulty list */
                BestTimes.novice.add(node);
                Collections.sort(BestTimes.novice);
                if (BestTimes.novice.size() > 10)
                    BestTimes.novice.remove(10);
                return BestTimes.novice.contains(node);
            case 2:     /* Insert into easy difficulty list */
                BestTimes.easy.add(node);
                Collections.sort(BestTimes.easy);
                if (BestTimes.easy.size() > 10)
                    BestTimes.easy.remove(10);
                return BestTimes.easy.contains(node);
            case 3:     /* Insert into medium difficulty list */
                BestTimes.medium.add(node);
                Collections.sort(BestTimes.medium);
                if (BestTimes.medium.size() > 10)
                    BestTimes.medium.remove(10);
                return BestTimes.medium.contains(node);
            case 4:     /* Insert into hard difficulty list */
                BestTimes.hard.add(node);
                Collections.sort(BestTimes.hard);
                if (BestTimes.hard.size() > 10)
                    BestTimes.hard.remove(10);
                return BestTimes.hard.contains(node);
            default:    /* Insert into expert difficulty list */
                BestTimes.expert.add(node);
                Collections.sort(BestTimes.expert);
                if (BestTimes.expert.size() > 10)
                    BestTimes.expert.remove(10);
                return BestTimes.expert.contains(node);
        }
    }


    /**
     * Returns a string representing the time in the format hh:mm:ss. Used for
     * text displaying purposes.
     *
     * @param time The time to represent in a string.
     * @return A string representing the time given, in the format hh:mm:ss.
     */
    protected static String timeToString(int time) {

        /* Get variables for the seconds, minutes, and hours */
        int sec = (time % 60);
        int min = (time / 60);
        int hrs = ((time / 60) / 60);

        /* Return time in hh:mm:ss format */
        if (hrs == 0)
            return String.format("%d:%02d", min, sec);
        else
            return String.format("%d:%02d:%02d", hrs, min, sec);
    }

} // End BestTimes class