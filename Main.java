/**
 * Main.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains the main method that is invoked when the user starts the program.
 */
package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// functionality is not architectural
public class Main {

    /* Invoked at runtime, starts and runs the program. */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        BufferedReader r = new BufferedReader(new FileReader("5.txt"));
        String l = r.readLine();
        int i = 0, j = 0;
        while (l != null) {
            SudokuPuzzle p = new SudokuPuzzle(l);
            i++;
            if (p.isComplete())
                j++;
            l = r.readLine();
        }
        System.out.printf("%d puzzles scanned. %d complete.\n", i, j);


//        Puzzle p = new Puzzle("");
//        p.print();
//        System.out.printf("\nIs Complete: %s\n\n", p.isComplete());
    }

}
