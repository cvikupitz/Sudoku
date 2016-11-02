/**
 * Main.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains the main method that is invoked when the user starts the program.
 */
package sudoku;


public class Main {

    /* Invoked at runtime, starts and runs the program. */
    public static void main(String[] args) {

        Puzzle p = new Puzzle("082001900064032718700850060900200087826105009007380026005600072098407630670028090");
        p.print();
        System.out.printf("\nIs Complete: %s\n\n", p.isComplete());
    }

}
