
package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolverTest {

    public static void main(String[] args) throws IOException {

        BufferedReader br;

        for (int i = 1; i <= 5; i++) {
            br = new BufferedReader(new FileReader(i + ".txt"));

            for (int j = 0; j < 10000; j++) {
                String line = br.readLine();
                SudokuPuzzle p = new SudokuPuzzle(line);
                SudokuSolver s = new SudokuSolver(p);
                if (!s.getSolution().isComplete()) {
                    System.err.println("Error - Sudoku Solver failed to solve a puzzle.");
                    System.exit(1);
                }
            } System.out.println(i + ".txt -- COMPLETE");
        } System.out.println("Sudoku Solver Test Passed!");
    }
}
