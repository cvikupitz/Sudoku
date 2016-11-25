/**
 * Settings.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


public class Settings {

    /* Declare private members */
    private static boolean showTimer = true;
    private static boolean showLegal = true;
    private static boolean highlight = true;
    private static boolean conflictingNumbers = true;


    /***/
    protected static boolean showTimer() {
        return Settings.showTimer;
    }

    /***/
    protected static boolean showLegal() {
        return Settings.showLegal;
    }

    /***/
    protected static boolean showHighlighted() {
        return Settings.highlight;
    }

    /***/
    protected static boolean showConflictingNumbers() {
        return Settings.conflictingNumbers;
    }

    /***/
    protected static void showTimer(boolean flag) {
        Settings.showTimer = flag;
    }

    /***/
    protected static void showLegal(boolean flag) {
        Settings.showLegal = flag;
    }

    /***/
    protected static void showHighlighted(boolean flag) {
        Settings.highlight = flag;
    }

    /***/
    protected static void showConflictingNumbers(boolean flag) {
        Settings.conflictingNumbers = flag;
    }
}
