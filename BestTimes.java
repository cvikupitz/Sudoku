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

    private static boolean insertNovice(int time) {
        for (int i = 0; i < 10; i++) {
            if (BestTimes.noviceBestTimes[i] == -1 || (time < BestTimes.noviceBestTimes[i])) {
                BestTimes.noviceBestTimes[i] = time;
                BestTimes.noviceBestDates[i] =
                        new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
                return true;
            }
        }
        return false;
    }

    private static boolean insertEasy(int time) {
        for (int i = 0; i < 10; i++) {
            if (BestTimes.easyBestTimes[i] == -1 || (time < BestTimes.easyBestTimes[i])) {
                BestTimes.easyBestTimes[i] = time;
                BestTimes.easyBestDates[i] =
                        new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
                return true;
            }
        }
        return false;
    }

    private static boolean insertMedium(int time) {
        for (int i = 0; i < 10; i++) {
            if (BestTimes.mediumBestTimes[i] == -1 || (time < BestTimes.mediumBestTimes[i])) {
                BestTimes.mediumBestTimes[i] = time;
                BestTimes.mediumBestDates[i] =
                        new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
                return true;
            }
        }
        return false;
    }

    private static boolean insertHard(int time) {
        for (int i = 0; i < 10; i++) {
            if (BestTimes.hardBestTimes[i] == -1 || (time < BestTimes.hardBestTimes[i])) {
                BestTimes.hardBestTimes[i] = time;
                BestTimes.hardBestDates[i] =
                        new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
                return true;
            }
        }
        return false;
    }

    private static boolean insertExpert(int time) {
        for (int i = 0; i < 10; i++) {
            if (BestTimes.expertBestTimes[i] == -1 || (time < BestTimes.expertBestTimes[i])) {
                BestTimes.expertBestTimes[i] = time;
                BestTimes.expertBestDates[i] =
                        new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
                return true;
            }
        }
        return false;
    }
} // End BestTimes class