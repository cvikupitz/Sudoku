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
        return new boolean[]{false, false, false, false, false, false, false, false, false};
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
        return !(!this.subGridOneIsComplete() ||
                !this.subGridTwoIsComplete() ||
                !this.subGridThreeIsComplete() ||
                !this.subGridFourIsComplete() ||
                !this.subGridFiveIsComplete() ||
                !this.subGridSixIsComplete() ||
                !this.subGridSevenIsComplete() ||
                !this.subGridEightIsComplete() ||
                !this.subGridNineIsComplete());
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
     * Returns a 2-d integer array representing the sudoku board.
     *
     * @return The 2-d array representing the sudoku board.
     */
    public int[][] toArray() {
        return this.board;
    }


    /**
     * Sets the sudoku board to the specified 2-d integer array. Used after
     * being passed through the sudoku solver.
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
    //  each subgrid.                                     //
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

    /* Checks the first subgrid for completenes. */
    private boolean subGridOneIsComplete() {
        int sum = 0;
        sum += this.board[0][0];
        sum += this.board[0][1];
        sum += this.board[0][2];
        sum += this.board[1][0];
        sum += this.board[1][1];
        sum += this.board[1][2];
        sum += this.board[2][0];
        sum += this.board[2][1];
        sum += this.board[2][2];
        return (sum == 45);
    }

    /* Checks the second subgrid for completenes. */
    private boolean subGridTwoIsComplete() {
        int sum = 0;
        sum += this.board[0][3];
        sum += this.board[0][4];
        sum += this.board[0][5];
        sum += this.board[1][3];
        sum += this.board[1][4];
        sum += this.board[1][5];
        sum += this.board[2][3];
        sum += this.board[2][4];
        sum += this.board[2][5];
        return (sum == 45);
    }

    /* Checks the third subgrid for completenes. */
    private boolean subGridThreeIsComplete() {
        int sum = 0;
        sum += this.board[0][6];
        sum += this.board[0][7];
        sum += this.board[0][8];
        sum += this.board[1][6];
        sum += this.board[1][7];
        sum += this.board[1][8];
        sum += this.board[2][6];
        sum += this.board[2][7];
        sum += this.board[2][8];
        return (sum == 45);
    }

    /* Checks the fourth subgrid for completenes. */
    private boolean subGridFourIsComplete() {
        int sum = 0;
        sum += this.board[3][0];
        sum += this.board[3][1];
        sum += this.board[3][2];
        sum += this.board[4][0];
        sum += this.board[4][1];
        sum += this.board[4][2];
        sum += this.board[5][0];
        sum += this.board[5][1];
        sum += this.board[5][2];
        return (sum == 45);
    }

    /* Checks the fifth subgrid for completenes. */
    private boolean subGridFiveIsComplete() {
        int sum = 0;
        sum += this.board[3][3];
        sum += this.board[3][4];
        sum += this.board[3][5];
        sum += this.board[4][3];
        sum += this.board[4][4];
        sum += this.board[4][5];
        sum += this.board[5][3];
        sum += this.board[5][4];
        sum += this.board[5][5];
        return (sum == 45);
    }

    /* Checks the sixth subgrid for completenes. */
    private boolean subGridSixIsComplete() {
        int sum = 0;
        sum += this.board[3][6];
        sum += this.board[3][7];
        sum += this.board[3][8];
        sum += this.board[4][6];
        sum += this.board[4][7];
        sum += this.board[4][8];
        sum += this.board[5][6];
        sum += this.board[5][7];
        sum += this.board[5][8];
        return (sum == 45);
    }

    /* Checks the seventh subgrid for completenes. */
    private boolean subGridSevenIsComplete() {
        int sum = 0;
        sum += this.board[6][0];
        sum += this.board[6][1];
        sum += this.board[6][2];
        sum += this.board[7][0];
        sum += this.board[7][1];
        sum += this.board[7][2];
        sum += this.board[8][0];
        sum += this.board[8][1];
        sum += this.board[8][2];
        return (sum == 45);
    }

    /* Checks the eighth subgrid for completenes. */
    private boolean subGridEightIsComplete() {
        int sum = 0;
        sum += this.board[6][3];
        sum += this.board[6][4];
        sum += this.board[6][5];
        sum += this.board[7][3];
        sum += this.board[7][4];
        sum += this.board[7][5];
        sum += this.board[8][3];
        sum += this.board[8][4];
        sum += this.board[8][5];
        return (sum == 45);
    }

    /* Checks the ninth subgrid for completenes. */
    private boolean subGridNineIsComplete() {
        int sum = 0;
        sum += this.board[6][6];
        sum += this.board[6][7];
        sum += this.board[6][8];
        sum += this.board[7][6];
        sum += this.board[7][7];
        sum += this.board[7][8];
        sum += this.board[8][6];
        sum += this.board[8][7];
        sum += this.board[8][8];
        return (sum == 45);
    }
    //</editor-fold>

} // End SudokuPuzzle class