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

    /* File path where the user's saved game/fastest times are stored */
    private static final String USER = System.getProperty("user.home");
    protected static final String PATH = (FileUtility.USER + "\\Sudoku\\");


    /**
     * Saves the specified sudoku puzzle in a text file named 'saved.txt' into
     * the user's computer.
     *
     * @param p The Sudoku puzzle to save.
     */
    protected static void saveGame(SudokuPuzzle p) {

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
            writer.write(p.currentPuzzleState());
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

            /* Sets the board to the read board, return the puzzle */
            p.setArray(board);
            return p;

        } catch (Exception e) {return null;}
    }




    /* ***** FIXME ***** */




} // End FileUtility class