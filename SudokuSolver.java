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
    private final int[][] board;
    private final boolean solvable;

    /* Default constructor */
    public SudokuSolver(SudokuPuzzle p) {
        this.puzzle = new SudokuPuzzle(p.currentPuzzleState());
        this.board = this.puzzle.toArray();
        this.solvable = this.solve(this.board);
    }


    /***/
    public boolean isSolvable() {
        return this.solvable;
    }


    /***/
    public SudokuPuzzle getSolution() {
        /* Returns the sudoku puzzle in a solved state. */
        this.puzzle.setArray(this.board);
        return this.puzzle;
    }


    /***/
    private boolean solve(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] != 0)
                    continue;

                for (int k = 1; k <= 9; k++) {
                    this.board[i][j] = k;
                    if (this.puzzle.insert(k, i, j) && solve(this.board))
                        return true;
                    this.board[i][j] = 0;
                }

                return false;
            }
        }

        return true;
    }

} // End SudokuSolver class