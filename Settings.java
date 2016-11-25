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
    private static boolean hints = true;
    private static boolean solutions = true;


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
    protected static boolean showHints() {
        return Settings.hints;
    }

    /***/
    protected static boolean showSolutions() {
        return Settings.solutions;
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

    /***/
    protected static void showHints(boolean flag) {
        Settings.hints = flag;
    }

    /***/
    protected static void showSolutions(boolean flag) {
        Settings.solutions = flag;
    }
}
