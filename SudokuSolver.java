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
        this.puzzle = new SudokuPuzzle(p.initialPuzzleState());
        this.solvable = this.solve(this.puzzle.toArray());
    }

    /**
     * Returns the sudoku puzzle in a solved state.
     *
     * @return The solved sudoku puzzle.
     */
    private boolean solve(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        board[i][j] = k;
                        if (isValid(board, i, j) && solve(board))
                            return true;
                        else
                            board[i][j] = 0;
                    }
                    return false;
                }
            }
        }
        this.puzzle.setArray(board);
        return true;
    }

    private boolean isValid(int[][] board, int r, int c) {
        //Row check
        boolean[] row = new boolean[9];
        for (int i = 0; i < 9; i++) {
            if (board[r][i] >= 1 && board[r][i] <= 9) {
                if (row[board[r][i] - 1] == false) {
                    row[board[r][i] - 1] = true;
                }
                else {
                    return false;
                }
            }
        }
        //Column check
        boolean[] col = new boolean[9];
        for (int i = 0; i < 9; i++) {
            if (board[i][c] >= 1 && board[i][c] <= 9) {
                if (col[board[i][c] - 1] == false) {
                    col[board[i][c] - 1] = true;
                }
                else {
                    return false;
                }
            }
        }
        //3x3 box check
        boolean[] box = new boolean[9];
        for (int i = (r/3) * 3; i < (r/3) * 3 + 3; i++) {
            for (int j = (c/3) * 3; j < (c/3) * 3 + 3; j++) {
                if (board[i][j] >= 1 && board[i][j] <= j) {
                    if (box[board[i][j] - 1] == false) {
                        box[board[i][j] - 1] = true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
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