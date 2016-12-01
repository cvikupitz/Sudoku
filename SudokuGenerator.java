/**
 * SudokuGenerator.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that contains a method for obtaining a newly generated Sudoku puzzle,
 * given a specified difficulty labeled 1-5.
 *
 * Resources:
 * https://en.wikipedia.org/wiki/Dancing_Links
 * http://blog.forret.com/2006/08/14/a-sudoku-challenge-generator/
 * https://arunabhghosal.wordpress.com/2015/04/26/generating-sudoku-puzzle/
 * https://github.com/SomeKittens/Sudoku-Project/blob/master/SudokuGenerator.java
 */
package sudoku;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SudokuGenerator {

    private SudokuPuzzle puzzle;

    public SudokuGenerator(int i) {
        this.puzzle = getPuzzle(i);
    }

    private SudokuPuzzle getPuzzle(int i) {

        try {
            InputStream stream = SudokuGenerator.class.getResourceAsStream(i + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            Random r = new Random();
            int k = r.nextInt(10000);
            String line = reader.readLine();
            for (int j = 0; j < k-1; j++)
                line = reader.readLine();

            SudokuPuzzle p = new SudokuPuzzle(line);
            p.setDifficulty(i);
            return p;
        } catch (Exception e) {return null;}
    }

    /**
     * Generates a new Sudoku puzzle given the specified difficulty ranked from 1
     * to 5, then returns it for play.
     *
     * @return A new Sudoku puzzle of the specified difficulty.
     */
    public SudokuPuzzle getPuzzle() {
        return this.puzzle;
    }

} // End SudokuGenerator class