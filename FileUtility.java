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
    protected static boolean fileNameValid(String s) {
        return s.matches("^[a-zA-Z0-9][a-zA-Z0-9\\s_/-]*([^\\s_/-])$");
    }


    /**
     * FIXME
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


    /***/
    protected static File getFile(String n, String path) {

        File folder = new File(path);
        File[] fileList = folder.listFiles();

        for (File file : fileList) {
            if (file.isFile() && file.toString().endsWith(".txt") &&
                    file.getName().toLowerCase().equals(n.toLowerCase()))
                return file;
        }
        return null;
    }

} // End FileUtility class