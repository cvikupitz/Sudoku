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

    /* Default constructor */
    public SudokuSolver(SudokuPuzzle p) {
        this.puzzle = p;
        this.solvable = this.solve();
    }


    /**
     * Returns the sudoku puzzle in a solved state.
     *
     * @return The solved sudoku puzzle.
     */
    private boolean solve() {

        int[][] board = this.puzzle.toArray();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {

                    for (int k = 1; k <= 9; k++) {
                        if (!this.puzzle.insert(k, i, j)) {
                            this.puzzle.insert(0, i, j);
                            continue;
                        }

                        board[i][j] = k;
                        this.puzzle.setArray(board);
                        if (this.solve()) {
                            //SudokuPuzzle.setArray(board);
                            return true;
                        }
                        else {
                            board[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        this.puzzle.setArray(board);
        //SudokuPuzzle.setArray(board);
        return true;
    }

    public boolean isSolvable() {
        return this.solvable;
    }

    public SudokuPuzzle getSolution() {
        if (!this.solvable)
            return null;
        return this.puzzle;
    }

} // End SudokuSolver class