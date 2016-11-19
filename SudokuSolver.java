/**
 * SudokuSolver.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that takes a Sudoku puzzle, solves and returns the solved puzzle.
 */
package sudoku;


public class SudokuSolver {

    /* Declare private members */
    private final SudokuPuzzle puzzle;
    private final boolean solvable;

    public SudokuSolver(SudokuPuzzle p) {
        this.puzzle = p;
        this.solvable = false;
        /* Solve the puzzle here */
    }

    public boolean isSolvable() {
        /* Returns true if puzzle has a solution, false otherwise. */
        return false;
    }

    public SudokuPuzzle getSolution() {
        /* Returns the sudoku puzzle in a solved state. */
        return null;
    }

} // End SudokuSolver class