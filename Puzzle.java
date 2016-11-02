/**
 * NinePuzzle.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


public class Puzzle {

    /* Declare private members */
    private final int[][] board;

    /* Default constructor */
    public Puzzle(String config) {
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
     * Checks the Sudoku puzzle for completeness. Returns true if the puzzle is
     * complete and all constraints are satisfied, or false if otherwise.
     *
     * @return True if the puzzle is complete, or false if not.
     */
    public boolean isComplete() {
        return false;
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

} // End Puzzle class