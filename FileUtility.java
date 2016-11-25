/**
 * FileUtility.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * File that contains methods for saving and loading the user's current played
 * game, and also the user's fastest times.
 */
package sudoku;


/* Imports */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtility {

    /* File paths where the user's saved game, fastest times, and puzzles are stored */
    protected static final String PATH = "C:/Sudoku/";
    protected static final String MY_PUZZLES_PATH = FileUtility.PATH + "My Puzzles/";

    /**
     * Saves the specified sudoku puzzle in a text file named 'saved.txt' into
     * the user's computer.
     *
     * @param p The Sudoku puzzle to save.
     * @param difficulty The difficulty of the puzzle, ranked 1-5.
     */
    protected static void saveGame(SudokuPuzzle p, int difficulty) {

        /* Puzzle is equal to null, return */
        if (p == null) {
            WindowUtility.errorMessage("An error occured while trying to save the puzzle.",
                    "Error Saving Puzzle");
            return;
        }

        /* Create a new file */
        File file = new File(FileUtility.PATH + "saved.txt");

        /* Open the file to write to */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(p.initialPuzzleState() + "\n");
            writer.write(p.currentPuzzleState() + "\n");
            writer.write(Integer.toString(difficulty));
            writer.close();
        } catch (Exception e) {
            WindowUtility.errorMessage("An error occured while trying to save the puzzle.",
                    "Error Saving Puzzle");
        }
    }


    /**
     * FIXME
     */
    protected static void saveGame(SudokuPuzzle p, int difficulty, String path) {

        /* Puzzle is equal to null, return */
        if (p == null) {
            WindowUtility.errorMessage("An error occured while trying to save the puzzle.",
                    "Error Saving Puzzle");
            return;
        }

        /* Create a new file */
        File file = new File(path);

        /* Open the file to write to */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(p.initialPuzzleState() + "\n");
            writer.write(p.currentPuzzleState() + "\n");
            writer.write(Integer.toString(difficulty));
            writer.close();
        } catch (Exception e) {
            WindowUtility.errorMessage("An error occured while trying to save the puzzle.",
                    "Error Saving Puzzle");
        }
    }


    /**
     * Loads the saved sudoku puzzle from the text file named 'saved.txt', and
     * transfers the information into a Sudoku puzzle and returns the puzzle.
     *
     * @return The Sudoku puzzle loaded from the saved file.
     * @throws java.io.IOException
     */
    protected static SudokuPuzzle loadGame() throws IOException {
        String line;
        SudokuPuzzle p;
        int[][] board;
        int k;

        /* Try to open and read the file containing the saved puzzle */
        try (BufferedReader reader = new BufferedReader(new FileReader(FileUtility.PATH + "saved.txt"))) {

            /* Reads the initial state of the puzzle */
            line = reader.readLine();
            p = new SudokuPuzzle(line);

            /* Creates a 2-d int array to store the saved game state */
            line = reader.readLine();
            k = 0;
            board = new int[9][9];

            /* Reads the state by each character, loads the array */
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board[i][j] = Integer.parseInt(Character.toString(line.charAt(k++)));
                }
            }

            /* Sets the puzzle's difficulty */
            line = reader.readLine();
            p.setDifficulty(Integer.parseInt(line));

            /* Sets the board to the read board, return the puzzle */
            p.setArray(board);
            return p;

        } catch (Exception e) {return null;}
    }


    /**
     * FIXME
     */
    protected static SudokuPuzzle loadGame(String path) throws IOException {
        String line;
        SudokuPuzzle p;
        int[][] board;
        int k;

        /* Try to open and read the file containing the saved puzzle */
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            /* Reads the initial state of the puzzle */
            line = reader.readLine();
            p = new SudokuPuzzle(line);

            /* Creates a 2-d int array to store the saved game state */
            line = reader.readLine();
            k = 0;
            board = new int[9][9];

            /* Reads the state by each character, loads the array */
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board[i][j] = Integer.parseInt(Character.toString(line.charAt(k++)));
                }
            }

            /* Sets the puzzle's difficulty */
            line = reader.readLine();
            p.setDifficulty(Integer.parseInt(line));

            /* Sets the board to the read board, return the puzzle */
            p.setArray(board);
            return p;

        } catch (Exception e) {return null;}
    }


    /**
     * Checks to see if the given file name is valid, and returns true if so, or
     * false otherwise. A file name is considered valid if it starts with a letter
     * or number, contains only letters, numbers, spaces, hyphens, backslashes, and
     * underscores, and ends with a letter or number.
     *
     * @param s The file name to check for validity.
     * @return True if the name is valid, or false if not.
     */
    protected static boolean fileNameValid(String s) {
        return s.matches("^[a-zA-Z0-9][a-zA-Z0-9\\s_/-]*([^\\s_/-])$");
    }


    /**
     * Checks to see if the given file name is unique to the set of files inside
     * the given directory. Invoked when th euser creates a new puzzle file, as
     * created duplicate file names is illegal.
     *
     * @param s The file name to check for uniqueness.
     * @param path The file path to scan in.
     * @return True if the file name in the given directory is unique, or false
     * otherwise.
     */
    protected static boolean nameIsUnique(String s, String path) {

        /* Obtain all saved address books from the folder */
        File folder = new File(path);
        File[] fileList = folder.listFiles();

        /* Scan each file name, make sure it isn't the same */
        for (File file : fileList) {
            if (file.isFile() && file.toString().endsWith(".txt") &&
                    file.getName().toLowerCase().equals(s.toLowerCase()))
                return false;
        }
        return true;
    }


    /**
     * Returns the file object with the specified name in the specified directory.
     * Returns null if no file with the specified name exists in the specified
     * directory.
     *
     * @param n The name of the file to obtain.
     * @param path The path or directory to look in.
     * @return The file object containing the specified name, or null if it
     * doesn't exist.
     */
    protected static File getFile(String n, String path) {

        /* Goes to the specified directory, gets a list of all the files there */
        File folder = new File(path);
        File[] fileList = folder.listFiles();

        /* Checks each file's name */
        for (File file : fileList) {
            if (file.isFile() && file.toString().endsWith(".txt") &&
                    file.getName().toLowerCase().equals(n.toLowerCase()))
                return file;
        }
        return null;
    }


    /**
     * Copies the contents of the file inside the specified source directory and
     * exports the file to the specified directory.
     *
     * @param path The directory to the file to copy.
     * @param dest The destination directory to export the copied file to.
     * @return True if the export was successful, false ohterwise.
     */
    protected static boolean copyFile(String path, String dest) {

        try {

            /* Opens two streams for copying */
            File sourceFile = new File(path);
            File destinationFile = new File(dest);
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            FileOutputStream fileOutputStream = new FileOutputStream(
                    destinationFile);

            /* Copies file contents by line */
            int bufferSize;
            byte[] bufffer = new byte[512];
            while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
                fileOutputStream.write(bufffer, 0, bufferSize);
            }

            /* Close streams */
            fileInputStream.close();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {return false;}
    }


    /***/
    protected static void loadSettings() {

        try (BufferedReader reader = new BufferedReader(new FileReader(FileUtility.PATH + "settings.txt"))) {

            if (reader.readLine().equals("true"))
                Settings.showTimer(true);
            else
                Settings.showTimer(false);
            if (reader.readLine().equals("true"))
                Settings.showLegal(true);
            else
                Settings.showLegal(false);
            if (reader.readLine().equals("true"))
                Settings.showHighlighted(true);
            else
                Settings.showHighlighted(false);
            if (reader.readLine().equals("true"))
                Settings.showConflictingNumbers(true);
            else
                Settings.showConflictingNumbers(false);
            reader.close();
        } catch (Exception e) {return;}

    }


    /***/
    protected static void saveSettings() {

        /* Create a new file */
        File file = new File(FileUtility.PATH + "settings.txt");

        /* Open the file to write to */
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Boolean.toString(Settings.showTimer()) + "\n");
            writer.write(Boolean.toString(Settings.showLegal()) + "\n");
            writer.write(Boolean.toString(Settings.showHighlighted()) + "\n");
            writer.write(Boolean.toString(Settings.showConflictingNumbers()));
            writer.close();
        } catch (Exception e) {
            WindowUtility.errorMessage("An error occured while trying to save the puzzle.",
                    "Error Saving Puzzle");
        }
    }

} // End FileUtility class