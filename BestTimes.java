/**
 * BestTimes.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.text.SimpleDateFormat;
import java.util.Date;

public class BestTimes {

    protected static int time = 0;
    protected static int[] noviceBestTimes = {-1, -1, -1, -1, -1,
                                                -1, -1, -1, -1, -1};
    protected static String[] noviceBestDates = {null, null, null,
                                    null, null, null, null, null, null, null};
    protected static int[] easyBestTimes = {-1, -1, -1, -1, -1,
                                                -1, -1, -1, -1, -1};
    protected static String[] easyBestDates = {null, null, null,
                                    null, null, null, null, null, null, null};
    protected static int[] mediumBestTimes = {-1, -1, -1, -1, -1,
                                                -1, -1, -1, -1, -1};
    protected static String[] mediumBestDates = {null, null, null,
                                    null, null, null, null, null, null, null};
    protected static int[] hardBestTimes = {-1, -1, -1, -1, -1,
                                                -1, -1, -1, -1, -1};
    protected static String[] hardBestDates = {null, null, null,
                                    null, null, null, null, null, null, null};
    protected static int[] expertBestTimes = {-1, -1, -1, -1, -1,
                                                -1, -1, -1, -1, -1};
    protected static String[] expertBestDates = {null, null, null,
                                    null, null, null, null, null, null, null};


    /***/
    protected static boolean insertBestTime(int time, int difficulty) {

        if (difficulty == 1)
            return BestTimes.insertNovice(time);
        if (difficulty == 2)
            return BestTimes.insertEasy(time);
        if (difficulty == 3)
            return BestTimes.insertMedium(time);
        if (difficulty == 4)
            return BestTimes.insertHard(time);
        else
            return BestTimes.insertExpert(time);
    }


    /***/
    protected static String timeToString(int time) {
        int sec = (time % 60);
        int min = (time / 60);
        int hrs = ((time / 60) / 60);

        if (hrs == 0)
            return String.format("%02d:%02d", min, sec);
        else
            return String.format("%d:%02d:%02d", hrs, min, sec);
    }


    
} // End BestTimes class