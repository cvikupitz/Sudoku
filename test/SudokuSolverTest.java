/**
 * SudokuSolverTest.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains a test for the SudokuSolver class. Run this file separately from
 * the package with the puzzle text files in the same directory named 1, 2, 3, 4,
 * and 5 .txt. The test will solve all puzzles and display a message when all
 * puzzles have been solved. If a puzzle could not be solved, an error message
 * appears along with the puzzle that failed the test.
 *
 * Runs slowly (~7.5 minutes to solve all puzzles), but does now work.
 */


/* Imports */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolverTest {

    public static void main(String[] args) throws IOException {

        /* Declare variables */
        BufferedReader br;

        /* Read each of the five test files */
        for (int i = 1; i <= 5; i++) {
            br = new BufferedReader(new FileReader("src/Sudoku/" + i + ".txt"));

            /* Read and solve each of the 10,000 stored puzzles */
            for (int j = 0; j < 10000; j++) {
                String line = br.readLine();
                SudokuPuzzle p = new SudokuPuzzle(line);
                SudokuSolver s = new SudokuSolver(p);

                /* The solver failed to solve a puzzle, print error message, stop build */
                if (!s.isSolvable()) {
                    System.err.println("Error - Sudoku Solver failed to solve a puzzle.");
                    System.out.println("Failed Puzzle: ");
                    p.print();
                    System.exit(1);
                }
            }
            System.out.println(i + ".txt -- COMPLETE");
        }
        /* Display message when all tests passed */
        System.out.println("Sudoku Solver Test Passed!");
    }

} // End SudokuSolverTest class