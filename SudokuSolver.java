/**
 * SudokuSolver.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that takes a sudoku puzzle , solves and returns the solved puzzle.
 */
package sudoku;


public class SudokuSolver {

    /* Declare private members */
    private final SudokuPuzzle puzzle;

    /* Default constructor */
    public SudokuSolver(SudokuPuzzle p) {
        this.puzzle = p;
    }


    /**
     * Returns the sudoku puzzle in a solved state.
     *
     * @return The solved sudoku puzzle.
     */
    public boolean getSolution() {
        int[][] board = this.puzzle.toArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        board[i][j] = k;
                        if (isValid(board, i, j) && solver(board)) {
                            SudokuPuzzle.setArray(board);
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
        SudokuPuzzle.setArray(board);
        return true;
    }

    public boolean isValid(int[][] board, int r, int c) {
        //Row check
        boolean[] row = new boolean[9];
        for (int i = 0; i < 9; i++) {
            if board[r][i] >= 1 && board[r][i] <= 9) {
                if (row[board[r][i] - 1] == false) {
                    row[board[r][i] - 1] = true;
                }
                else {
                    return false;
                }
            }
        }
        //Column check
        boolean col = new boolean[9];
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

} // End SudokuSolver class