/**
 * SudokuFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * A data structure representing a stack that holds all of the player's moves
 * during a game of sudoku. Used for undoing player's moves.
 */
package sudoku;


public class MoveStack {

    /* Internal class that represents a node stored inside the stack. */
    private class Node {

        /* Declare private members */
        private final String val;
        private Node prev, next;

        /* Default constructor */
        private Node(String s) {
            this.val = s;
            this.prev = this.next = null;
        }

        /* Returns the node's stored string */
        private String getVal() {
            return this.val;
        }

        /* Returns the previous linked node */
        private Node prev() {
            return this.prev;
        }

        /* Returns the next linked node */
        private Node next() {
            return this.next;
        }

        /* Sets the node's previous linked node */
        private void setNext(Node node) {
            this.next = node;
        }

        /* Sets the node's next linked node */
        private void setPrev(Node node) {
            this.prev = node;
        }

    } // End Node class


    /* Declare private members */
    private final int CAPACITY = 500;
    private Node top, bottom;
    private int size;

    /* Default constructor */
    public MoveStack() {
        this.top = this.bottom = null;
        this.size = 0;
    }


    /**
     * Clears out the stack of all of its elements.
     */
    public void clear() {
        this.top = this.bottom = null;
        this.size = 0;
    }


    /**
     * Returns the value currently stored at the top of the stack.
     *
     * @return The vlaue at the top of the stack.
     */
    public String peek() {
        return this.top.getVal();
    }


    /**
     * Inserts the specified value s into the stack.
     *
     * @param s The string representing the command to insert.
     */
    public void push(String s) {
        Node n = new Node(s);

        if (this.isEmpty()) {
            this.top = this.bottom = n;
        } else {
            n.setNext(this.top);
            this.top.setPrev(n);
            this.top = n;
        } this.size++;

        /* Remove bottom node if exceeding capacity */
        if (this.size > this.CAPACITY)
            this.removeBottomNode();
    }


    /*
    Removes the bottom node from the stack if the recent insert causes the
    stack's size to exceed its capacity.
    */
    private void removeBottomNode() {
        this.bottom = this.bottom.prev();
        this.bottom.setNext(null);
        this.size--;
    }


    /**
     * Pops, or removes, the top element from the stack, and returns the string
     * representing the command.
     *
     * @return The string representing the command that was popped from the stack.
     */
    public String pop() {
        if (this.isEmpty())
            return null;

        Node n = this.top;
        this.top = this.top.next();
        this.size--;
        return n.getVal();
    }


    /**
     * Returns true if the deque is currently empty, or false if otherwise.
     *
     * @return True if empty, false if not.
     */
    public boolean isEmpty() {
        return (this.size == 0);
    }

} // End MoveQueue class