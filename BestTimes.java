/**
 * BestTimes.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


public class BestTimes {

    /**/
    protected static int time = 0;

    /**/
    protected static HighScoreList novice = new HighScoreList();

    /**/
    protected static HighScoreList easy = new HighScoreList();

    /**/
    protected static HighScoreList medium = new HighScoreList();

    /**/
    protected static HighScoreList hard = new HighScoreList();

    /**/
    protected static HighScoreList expert = new HighScoreList();

    /***/
    protected static boolean insertBestTime(int time, int difficulty) {

        if (difficulty == 1)
            return BestTimes.novice.insertScore(time);
        if (difficulty == 2)
            return BestTimes.easy.insertScore(time);
        if (difficulty == 3)
            return BestTimes.medium.insertScore(time);
        if (difficulty == 4)
            return BestTimes.hard.insertScore(time);
        else
            return BestTimes.expert.insertScore(time);
    }


    /***/
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