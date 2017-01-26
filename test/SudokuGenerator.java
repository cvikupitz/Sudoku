/**
 * SudokuGenerator.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that contains a method for obtaining a newly generated Sudoku puzzle,
 * given a specified difficulty labeled 1-5; 1 = novice, 2 = easy, 3 = medium,
 * 4 = hard, 5+ = expert.
 */


/* Imports */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class SudokuGenerator {

    /* Declare private members */
    private final SudokuPuzzle puzzle;


    /* Default constructor */
    public SudokuGenerator(int i) {

        /* Resets the difficulty variables if too low/high */
        if (i < 1)
            i = 1;
        else if (i > 5)
            i = 5;

        /* Generates the puzzle */
        this.puzzle = getPuzzle(i);
    }


    /**
     * Private method invoked in the constructor. Reads from the specified text
     * file corresponding to the desired difficulty, achieves a random sudoku
     * puzzle from the file.
     */
    private SudokuPuzzle getPuzzle(int i) {

        /* Attempts to read from one of the resource text files containing the puzzles */
        try {

            /* Creates the streams to read from the file */
            InputStream stream = SudokuGenerator.class.getResourceAsStream(i + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            /* Chooses a random number; randomly selects a puzzle from the file */
            Random r = new Random();
            int k = r.nextInt(10000);
            String line = reader.readLine();

            /* Reads through k puzzles */
            for (int j = 0; j < k-1; j++)
                line = reader.readLine();

            /* Obtains and returns the puzzle, close the reader */
            SudokuPuzzle p = new SudokuPuzzle(line);
            p.setDifficulty(i);
            reader.close();
            return p;

        } catch (Exception e) {return null;}
    }


    /**
     * Returns the Sudoku puzzle generated, or null if the generation failed.
     *
     * @return The generated Sudoku puzzle, or null if failed.
     */
    public SudokuPuzzle getPuzzle() {
        return this.puzzle;
    }

} // End SudokuGenerator class