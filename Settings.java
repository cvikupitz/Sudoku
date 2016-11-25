/**
 * Settings.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


public class Settings {

    /* Declare private members */
    private static boolean showTimer;
    private static boolean showLegal;
    private static boolean highlight;
    private static boolean conflictingNumbers;


    /***/
    protected boolean showTimer() {
        return Settings.showTimer;
    }

    /***/
    protected boolean showLegal() {
        return Settings.showLegal;
    }

    /***/
    protected boolean showHighlighted() {
        return Settings.highlight;
    }

    /***/
    protected boolean showConflictingNumbers() {
        return Settings.conflictingNumbers;
    }

    /***/
    protected void showTimer(boolean flag) {
        Settings.showTimer = flag;
    }

    /***/
    protected void showLegal(boolean flag) {
        Settings.showLegal = flag;
    }

    /***/
    protected void showHighlighted(boolean flag) {
        Settings.highlight = flag;
    }

    /***/
    protected void showConflictingNumbers(boolean flag) {
        Settings.conflictingNumbers = flag;
    }
}
