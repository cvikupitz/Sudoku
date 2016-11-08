/**
 * SudokuPuzzle.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that represents a sudoku puzzle. A string is passed into the constructor
 * containing 81 characters/numbers, and the board is constructed from that.
 * The board is represetned as a 9x9 2-dimensional integer array.
 */
package sudoku;


public class SudokuPuzzle {

    /* Declare private members */
    private final String config;
    private int[][] board;

    /* Default constructor */
    public SudokuPuzzle() {
        this("000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    }

    /* Default constructor */
    public SudokuPuzzle(String config) {
        this.config = config;
        this.board = new int[9][9];
        char[] chars = config.toCharArray();
        int temp, index = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp = Character.getNumericValue(chars[index++]);
                if (temp == -1)
                    this.board[i][j] = 0;
                else
                    this.board[i][j] = temp;
            }
        }
    }


    /**
     * Inserts the specified number into the sudoku board and the specified
     * coordinates. If the number inserted was a legal number, true is returned,
     * or false if not.
     *
     * @param val The number to insert into the sudoku board, must be 1-9.
     * @param i The row to insert the number into.
     * @param j The column to insert the number into.
     * @return True if the number insertion was legal, false if not.
     */
    public boolean insert(int val, int i, int j) {
        if (1 > val || val > 9 || 0 > i || i > 8 || 0 > j || j > 8)
            return false;

        this.board[i][j] = val;
        boolean[] legalMoves = this.getLegalMoves(i, j);
        return legalMoves[val-1];
    }


    /**
     * Removes the value at the specified coordinates from the Sudoku board.
     *
     * @param i The row the value is in to be deleted.
     * @param j The column the value is in to be deleted.
     */
    public void remove(int i, int j) {
        if (0 > i || i > 8 || 0 > j || j > 8)
            return;
        this.board[i][j] = 0;
    }


    /**
     * Returns a list of booleans representing the list of legal numbers that
     * can be inserted into the specified square. Index 0 represents a 1, index
     * 1 represents a 2, and so forth. True indicates that the number is legal,
     * and false indicates it is not.
     *
     * @param i The row the square is in.
     * @param j The column the square is in.
     * @return A list of booleans representing the legal moves to make.
     */
    public boolean[] getLegalMoves(int i, int j) {
        boolean[] legalMoves = {true, true, true, true, true, true, true, true, true};
        legalMoves = this.legalMovesInRow(i, legalMoves);
        legalMoves = this.legalMovesInColumn(j, legalMoves);
        legalMoves = this.legalMovesInSubGrid(i, j, legalMoves);
        return legalMoves;
    }


    /**
     * Checks the Sudoku puzzle for completeness. Returns true if the puzzle is
     * complete and all constraints are satisfied, or false if otherwise.
     *
     * @return True if the puzzle is complete, or false if not.
     */
    public boolean isComplete() {

        /* Check all rows */
        for (int i = 0; i < 9; i++) {
            if (!this.rowIsComplete(i))
                return false;
        }

        /* Check all columns */
        for (int i = 0; i < 9; i++) {
            if (!this.columnIsComplete(i))
                return false;
        }

        /* Check all subgrids */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!this.subGridIsComplete(i*3, j*3))
                    return false;
            }
        }

        /* All constraints satisfied */
        return true;
    }


    /**
     * Prints out the sudoku puzzle in the command prompt.
     */
    public void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] != 0)
                    System.out.print(this.board[i][j] + " ");
                else
                    System.out.print(". ");
                if (j == 2 || j == 5)
                    System.out.print("| ");
            }
            System.out.println();
            if (i == 2 || i == 5)
                System.out.println("------+-------+------");
        }
    }


    /**
     * Resets the Sudoku puzzle back to it's initial starting state.
     */
    public void resetPuzzle() {
        this.board = new int[9][9];
        char[] chars = this.config.toCharArray();
        int temp, index = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp = Character.getNumericValue(chars[index++]);
                if (temp == -1)
                    this.board[i][j] = 0;
                else
                    this.board[i][j] = temp;
            }
        }
    }


    /**
     * Returns a copy of this sudoku puzzle.
     *
     * @return A copy of this sudoku puzzle.
     */
    public SudokuPuzzle getCopy() {
        SudokuPuzzle p = this;
        return p;
    }


    /**
     * Returns a 2-d integer array representing the sudoku board.
     *
     * @return The 2-d array representing the sudoku board.
     */
    public int[][] toArray() {
        return this.board;
    }


    /**
     * Sets the sudoku board to the specified 2-d integer array. Used after
     * being passed through the sudoku solver and loading from a file.
     *
     * @param b The array to set the sudoku board to.
     */
    protected void setArray(int[][] b) {
        this.board = b;
    }


    /**
     * Returns the string representing the Sudoku puzzle when it was first
     * created. Used for saving the user's progress midgame.
     *
     * @return A string representing the initial state of the Sudoku puzzle.
     */
    protected String initialPuzzleState() {
        return this.config;
    }


    /**
     * Returns the string representing the Sudoku puzzle in its current state.
     * Used for saving the user's progress midgame.
     *
     * @return A string representing the current state of the Sudoku puzzle.
     */
    protected String currentPuzzleState() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s += this.board[i][j];
            }
        }
        return s;
    }


    ////////////////////////////////////////////////////////
    //       --- Completion Checker Methods --            //
    //  These methods are invoked when checking the       //
    //  sudoku board for completeness. Contains a         //
    //  method for checking a row, a column, and one for  //
    //  the subgrid.                                      //
    ////////////////////////////////////////////////////////
    //<editor-fold defaultstate="collapsed" desc=" Sudoku puzzle checking functions ">

    /*
    Checks if the given row of the sudoku board is complete. Returns true if
    so, or false if otherwise.
    */
    private boolean rowIsComplete(int r) {
        boolean[] row = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 9; i++) {
            if (this.board[r][i] == 0)
                return false;
            else if (row[this.board[r][i]-1])
                return false;
            else
                row[this.board[r][i]-1] = true;
        }
        return true;
    }

    /*
    Checks if the given column of the sudoku board is complete. Returns true if
    so, or false if otherwise.
    */
    private boolean columnIsComplete(int c) {
        boolean[] row = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 9; i++) {
            if (this.board[i][c] == 0)
                return false;
            else if (row[this.board[i][c]-1])
                return false;
            else
                row[this.board[i][c]-1] = true;
        }
        return true;
    }

    /* Checks the subgrid in the specified location for completion. */
    private boolean subGridIsComplete(int x, int y) {
        boolean[] grid = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((x / 3) * 3 + (i % 3));
                int n = ((y / 3) * 3 + (j % 3));
                if (this.board[m][n] == 0)
                    return false;
                else if (grid[this.board[m][n]-1])
                    return false;
                else
                    grid[this.board[m][n]-1] = true;
            }
        }
        return true;
    }
    //</editor-fold>

    ////////////////////////////////////////////////////////
    //       --- Legal Moves Helper Methods --            //
    //  These methods are invoked when checking a space   //
    //  on the sudoku board for the list of legal moves.  //
    //  Contains a method for checking a row, a column,   //
    //  and one for the subgrid.                          //
    ////////////////////////////////////////////////////////
    //<editor-fold defaultstate="collapsed" desc=" Sudoku puzzle legal moves checker functions ">

    /*
    FIXME
    */
    private boolean[] legalMovesInRow(int r, boolean[] list) {
        for (int i = 0; i < 9; i++) {
            if (this.board[r][i] != 0)
                list[this.board[r][i]-1] = false;
        }
        return list;
    }

    /*
    FIXME
    */
    private boolean[] legalMovesInColumn(int c, boolean[] list) {
        for (int i = 0; i < 9; i++) {
            if (this.board[i][c] != 0)
                list[this.board[i][c]-1] = false;
        }
        return list;
    }

    /* FIXME */
    private boolean[] legalMovesInSubGrid(int x, int y, boolean[] list) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((x / 3) * 3 + (i % 3));
                int n = ((y / 3) * 3 + (j % 3));
                if (this.board[m][n] != 0)
                    list[this.board[m][n]-1] = false;
            }
        }
        return list;
    }
    //</editor-fold>

} // End SudokuPuzzle class