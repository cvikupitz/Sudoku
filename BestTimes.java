/**
 * BestTimes.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains static linked lists that stores the user's top 10 times in each
 * difficulty, including novice, easy, medium, hard, and expert puzzles. Also
 * contains a method for inserting scores into the lists that will accept or
 * decline the score as a top-10 score, and rearrange the list if needed. Also
 * contains a method for getting a string representation of the date the score
 * was achieved, used for displaying purposes.
 */
package sudoku;


public class BestTimes {

    /* Static variable to temporarily store a time for resuming/saving a game */
    protected static int time = 0;

    /* Linked list to store the best novice puzzle times */
    protected static HighScoreList novice = new HighScoreList();

    /* Linked list to store the best easy puzzle times */
    protected static HighScoreList easy = new HighScoreList();

    /* Linked list to store the best medium puzzle times */
    protected static HighScoreList medium = new HighScoreList();

    /* Linked list to store the best hard puzzle times */
    protected static HighScoreList hard = new HighScoreList();

    /* Linked list to store the best expert puzzle times */
    protected static HighScoreList expert = new HighScoreList();

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
        if (difficulty == 1)
            return BestTimes.novice.insertScore(node);
        if (difficulty == 2)
            return BestTimes.easy.insertScore(node);
        if (difficulty == 3)
            return BestTimes.medium.insertScore(node);
        if (difficulty == 4)
            return BestTimes.hard.insertScore(node);
        else
            return BestTimes.expert.insertScore(node);
    }


    /**
     * Returns a string representing the time in the format hh:mm:ss. Used for
     * text displaying purposes.
     *
     * @param time The time to represent in a string.
     * @return A string representing the time given, in the format hh:mm:ss.
     */
    protected static String timeToString(int time) {
        int sec = (time % 60);
        int min = (time / 60);
        int hrs = ((time / 60) / 60);
        if (hrs == 0)
            return String.format("%d:%02d", min, sec);
        else
            return String.format("%d:%02d:%02d", hrs, min, sec);
    }

} // End BestTimes class