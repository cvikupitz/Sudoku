/**
 * WindowUtility.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains static methods useful for displaying messages and prompting users
 * for information through a JFrame. Useful for applications with UI's.
 */
package sudoku;


/* Imports */
import java.awt.Toolkit;
import javax.swing.JOptionPane;

public class WindowUtility {

    /**
     * Displays an information window with a message to the user.
     *
     * @param msg The message to display.
     * @param title The title of the informational window.
     */
    public static void displayInfo(String msg, String title) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk");
        if (runnable != null)
            runnable.run();
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prompts the user to enter a string through a prompt window, then returns that
     * string for use.
     *
     * @param msg The message of the prompt box.
     * @return The string the user entered.
     */
    public static String getEntry(String msg) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk");
        if (runnable != null)
            runnable.run();
        return JOptionPane.showInputDialog(null, msg, "");
    }

    /**
     * Asks the user a question through a ask yes or no prompt window. Returns true
     * if the user selects yes, or no if not.
     *
     * @param qu The question to ask the user.
     * @param title The title of the question window.
     * @return True if user clicks yes, false if otherwise.
     */
    public static boolean askYesNo(String qu, String title) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
        if (runnable != null)
            runnable.run();
        return JOptionPane.showConfirmDialog(null, qu, title,
               JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /**
     * Displays an error window with a message to the user.
     *
     * @param msg The error message to display to the user.
     * @param title The title of the error window.
     */
    public static void errorMessage(String msg, String title) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand");
        if (runnable != null)
            runnable.run();
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

} // End WindowUtility class