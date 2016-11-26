/**
 * Settings.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that contains static members and variables that stores the program's
 * current settings. Contains methods for accessing and changing the program
 * settings.
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


    /**
     * Returns true if the timer feature is currently enabled, or false if not.
     *
     * @return True if the timer feature is on, false if not.
     */
    protected static boolean showTimer() {
        return Settings.showTimer;
    }


    /**
     * Returns true if the legal numbers allowed panel feature is currently
     * enabled, or false if not.
     *
     * @return True if the legal numbers display feature is on, false if not.
     */
    protected static boolean showLegal() {
        return Settings.showLegal;
    }


    /**
     * Returns true if the highlighter feature is currently enabled, or false
     * if not.
     *
     * @return True if the highlighter feature is on, false if not.
     */
    protected static boolean showHighlighted() {
        return Settings.highlight;
    }


    /**
     * Returns true if the illegally inserted number highlighter feature is
     * currently enabled, or false if not.
     *
     * @return True if the illegally inserted number highlighter feature is on,
     * false if not.
     */
    protected static boolean showConflictingNumbers() {
        return Settings.conflictingNumbers;
    }


    /**
     * Returns true if the hint feature is currently enabled, or false if not.
     *
     * @return True if the hint feature is on, false if not.
     */
    protected static boolean showHints() {
        return Settings.hints;
    }

    /**
     * Returns true if the solution feature is currently enabled, or false if not.
     *
     * @return True if the solution feature is on, false if not.
     */
    protected static boolean showSolutions() {
        return Settings.solutions;
    }


    /**
     * Sets the timer feature on or off given a boolean.
     *
     * @param flag The boolean to set the feature on/off; true to switch on, false
     * to switch off.
     */
    protected static void showTimer(boolean flag) {
        Settings.showTimer = flag;
    }


    /**
     * Sets the legal numbers panel feature on or off given a boolean.
     *
     * @param flag The boolean to set the feature on/off; true to switch on, false
     * to switch off.
     */
    protected static void showLegal(boolean flag) {
        Settings.showLegal = flag;
    }


    /**
     * Sets the highlighter feature on or off given a boolean.
     *
     * @param flag The boolean to set the feature on/off; true to switch on, false
     * to switch off.
     */
    protected static void showHighlighted(boolean flag) {
        Settings.highlight = flag;
    }


    /**
     * Sets the illegally inserted number highlighter feature on or off given a
     * boolean.
     *
     * @param flag The boolean to set the feature on/off; true to switch on, false
     * to switch off.
     */
    protected static void showConflictingNumbers(boolean flag) {
        Settings.conflictingNumbers = flag;
    }


    /**
     * Sets the hint feature on or off given a boolean.
     *
     * @param flag The boolean to set the feature on/off; true to switch on, false
     * to switch off.
     */
    protected static void showHints(boolean flag) {
        Settings.hints = flag;
    }


    /**
     * Sets the solution feature on or off given a boolean.
     *
     * @param flag The boolean to set the feature on/off; true to switch on, false
     * to switch off.
     */
    protected static void showSolutions(boolean flag) {
        Settings.solutions = flag;
    }

} // End Settings class