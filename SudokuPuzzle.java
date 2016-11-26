/**
 * SudokuPuzzle.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that represents a Sudoku puzzle. A string is passed into the constructor
 * containing 81 characters/numbers, and the board is constructed from that.
 * The board is represetned as a 9x9 2-dimensional integer array.
 */
package sudoku;


public class SudokuPuzzle {

    /* Declare private members */
    private int difficulty;
    private String initialState;
    private int[][] board;

    /* Default constructor */
    public SudokuPuzzle() {
        this("000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    }

    /* Default constructor */
    public SudokuPuzzle(String init) {
        this.difficulty = 0;
        this.initialState = init;
        this.board = new int[9][9];
        char[] chars = init.toCharArray();
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
     * @param val The number to insert into the Sudoku board, must be 1-9.
     * @param r The row to insert the number into.
     * @param c The column to insert the number into.
     * @return True if the number insertion was legal, false if not.
     */
    public boolean insert(int val, int r, int c) {
        if (1 > val || val > 9 || 0 > r || r > 8 || 0 > c || c > 8)
            return false;
        this.board[r][c] = val;
        boolean[] legalMoves = this.getLegalMoves(r, c);
        return legalMoves[this.board[r][c]-1];
    }


    /**
     * Removes the value at the specified coordinates from the Sudoku board.
     *
     * @param r The row the value is in to be deleted.
     * @param c The column the value is in to be deleted.
     */
    public void remove(int r, int c) {
        if (0 > r || r > 8 || 0 > c || c > 8)
            return;
        this.board[r][c] = 0;
    }


    /**
     * Returns a list of booleans representing the list of legal numbers that
     * can be inserted into the specified square. Index 0 represents a 1, index
     * 1 represents a 2, and so forth. True indicates that the number is legal,
     * and false indicates it is not.
     *
     * @param r The row the tile is in.
     * @param c The column the tile is in.
     * @return A list of booleans representing the legal moves to make.
     */
    public boolean[] getLegalMoves(int r, int c) {
        boolean[] legalMoves = {true, true, true, true, true, true, true, true, true};
        legalMoves = this.legalMovesInRow(r, c, legalMoves);
        legalMoves = this.legalMovesInColumn(c, r, legalMoves);
        legalMoves = this.legalMovesInSubGrid(r, c, legalMoves);
        return legalMoves;
    }


    /**
     * Returns the number held at the specified tile on the board. If the tile
     * is empty, 0 is returned.
     *
     * @param r The row to get the value at.
     * @param c The column to get the value at.
     * @return The number in the specified tile, or 0 if it's empty.
     */
    public int getValue(int r, int c) {
        return this.board[r][c];
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
                if (!this.subGridIsComplete(i * 3, j * 3))
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
     * Resets the Sudoku puzzle back to it's initial starting state. Invoked
     * when the user resets the game.
     */
    public void resetPuzzle() {
        this.board = new int[9][9];
        char[] chars = this.initialState.toCharArray();
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
     * Completely resets the puzzle back to an empty state, and resets the
     * puzzle states.
     */
    protected void hardReset() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                this.board[i][j] = 0;
        this.initialState = this.currentPuzzleState();
    }


    /**
     * Returns the number of filled tiles in the puzzle. A tile is filled if it
     * holds a number 1-9.
     *
     * @return The number of tiles filled on the Sudoku puzzle, from 0-81.
     */
    protected int getNumberFilled() {
        int amt = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] != 0)
                    amt++;
            }
        }
        return amt;
    }


    /**
     * Returns a 2-d integer array representing the Sudoku board.
     *
     * @return The 2-d array representing the Sudoku board.
     */
    public int[][] toArray() {
        return this.board;
    }


    /**
     * Sets the Sudoku board to the specified 2-d integer array. Used after
     * being passed through the Sudoku solver and loading from a file.
     *
     * @param b The array to set the sudoku board to.
     */
    protected void setArray(int[][] b) {
        this.board = b;
    }


    /**
     * Returns the puzzle's difficulty, ranging 1 to 5 from easiest to hardest.
     * Used to display the difficulty in the Sudoku JFrame.
     *
     * @return The puzzle's dificulty, from 1-5.
     */
    protected int getDifficulty() {
        return this.difficulty;
    }


    /**
     * Sets the puzzle's difficulty to the specified number. Used to display the
     * difficulty in the Sudoku JFrame.
     *
     * @param d The number to set the puzzle difficulty to.
     */
    protected void setDifficulty(int d) {
        this.difficulty = d;
    }


    /**
     * Returns the string representing the Sudoku puzzle when it was first
     * created. Used for saving the user's progress midgame.
     *
     * @return A string representing the initial state of the Sudoku puzzle.
     */
    protected String initialPuzzleState() {
        return this.initialState;
    }


    /**
     * Sets the Sudoku's initial state to the specified string. Used for saving
     * modified custom puzzles.
     *
     * @param s The initial state to set the puzzle in.
     */
    protected void setInitialPuzzleState(String s) {
        this.initialState = s;
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

    /**
     * Returns true if the specified row is complete, as in, it contains all
     * numbers 1-9, or false if not.
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

    /**
     * Returns true if the specified column is complete, as in, it contains all
     * numbers 1-9, or false if not.
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

    /**
     * Returns true if the subgrid at the specified location is complete, as in,
     * it contains all numbers 1-9, or false if not.
     */
    private boolean subGridIsComplete(int r, int c) {
        boolean[] grid = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((r / 3) * 3 + (i % 3));
                int n = ((c / 3) * 3 + (j % 3));
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
    //       --- Conflicting Square Methods --            //
    //  These methods are invoked when checking a space   //
    //  on the sudoku board for the list of squares that  //
    //  conflict with the given square. Contains a method //
    //  for checking a row, a column, and one for the     //
    //  subgrid.                                          //
    ////////////////////////////////////////////////////////
    //<editor-fold defaultstate="collapsed" desc=" Sudoku puzzle illegal checker functions ">

    /**
     * Returns a string of coordinates of the tiles in the row that conflict
     * with the specified tile.
     */
    private String getConflictingRow(int r, int c, String squares) {

        for (int k = 0; k < 9; k++) {
            if (k == c)
                continue;

            if (this.board[r][c] == this.board[r][k])
                squares += String.format("(%d %d) ", r, k);
        }
        return squares;
    }


    /**
     * Returns a string of coordinates of the tiles in the column that conflict
     * with the specified tile.
     */
    private String getConflictingColumn(int c, int r, String squares) {
        for (int k = 0; k < 9; k++) {
            if (k == r)
                continue;
            if (this.board[r][c] == this.board[k][c])
                squares += String.format("(%d %d) ", k, c);
        }
        return squares;
    }


    /**
     * Returns a string of coordinates of the tiles in the subgrid that conflict
     * with the specified tile.
     */
    private String getConflictingSubGrid(int r, int c, String squares) {
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((r / 3) * 3 + (i % 3));
                int n = ((c / 3) * 3 + (j % 3));
                if (r == m && c == n)
                    continue;
                if (this.board[m][n] == this.board[r][c])
                    squares += String.format("(%d %d) ", m, n);
            }
        }
        return squares;
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

    /**
     * Returns an array of moves that are legal within the specified row.
     */
    private boolean[] legalMovesInRow(int r, int c, boolean[] list) {
        for (int i = 0; i < 9; i++) {
            if (i == c)
                continue;
            if (this.board[r][i] != 0)
                list[this.board[r][i]-1] = false;
        }
        return list;
    }

    /**
     * Returns an array of moves that are legal within the specified column.
     */
    private boolean[] legalMovesInColumn(int c, int r, boolean[] list) {
        for (int i = 0; i < 9; i++) {
            if (i == r)
                continue;
            if (this.board[i][c] != 0)
                list[this.board[i][c]-1] = false;
        }
        return list;
    }

    /**
     * Returns an array of moves that are legal within the specified subgrid.
     */
    private boolean[] legalMovesInSubGrid(int r, int c, boolean[] list) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((r / 3) * 3 + (i % 3));
                int n = ((c / 3) * 3 + (j % 3));
                if (r == m && c == n)
                    continue;
                if (this.board[m][n] != 0)
                    list[this.board[m][n]-1] = false;
            }
        }
        return list;
    }
    //</editor-fold>

}