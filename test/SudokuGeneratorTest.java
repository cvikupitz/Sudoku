/**
 * SudokuGeneratorTest.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains a test for the SudokuGenerator class. Run this file separately from
 * the package and pass in a command line argument for the number of puzzles to
 * generate and test. By default, 100 are tested if no argument given, and can
 * test up to 10,000 puzzles. Displays the number of puzzles created and solved.
 *
 * Usage: java SudokuGeneratorTest n=NUMBER_OF_TEST_PUZZLES
 */


/* Imports */
import java.util.Random;

public class SudokuGeneratorTest {

    public static void main(String[] args) {

        /* Get the number of iterations to perform */
        int iter;
        if (args.length == 0)
            iter = 100;
        else
            iter = Integer.parseInt(args[0]);

        /* Runs the test */
        Random r = new Random();
        int solvable = 0;
        for (int i = 0; i < iter; i++) {

            /* Creates a puzzle of a random difficulty, attempts to solve it */
            SudokuGenerator gen = new SudokuGenerator(r.nextInt(5) + 1);
            SudokuPuzzle p = gen.getPuzzle();
            SudokuSolver s = new SudokuSolver(p);
            if (s.isSolvable())
                solvable++;
        }

        /* Displays the results */
        System.out.println("********** TEST COMPLETE **********");
        System.out.printf("%d sudoku puzzles generated.\n", iter);
        System.out.printf("%d of the puzzles are solvable.\n", solvable);
        System.out.printf("---- %d%% ----\n", (int)(((float)solvable / iter)) * 100);
        System.out.println("***********************************");
    }

} // End SudokuGeneratorTest class