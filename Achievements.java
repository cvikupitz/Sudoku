/**
 * Achievements.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


public class Achievements {

    protected static int[] puzzles = {0, 0, 0, 0, 0, 0};
    protected static boolean[] competitive = {false, false, false, false, false};
    protected static int[] handicapped = {0, 0, 0, 0, 0};
    protected static int[] assistant = {0, 0};
    protected static boolean[] engineer = {false, false, false, false, false,
                                           false, false};

    /***/
    protected void reset() {
        Achievements.puzzles = new int[]{0, 0, 0, 0, 0, 0};
        Achievements.competitive = new boolean[]{false, false, false, false, false};
        Achievements.handicapped = new int[]{0, 0, 0, 0, 0};
        Achievements.assistant = new int[]{0, 0};
        Achievements.engineer = new boolean[]{false, false, false, false, false,
                                              false, false};
    }

    
} // End Achievements class