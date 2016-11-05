/**
 * SudokuFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.UIDefaults;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SudokuFrame extends JFrame {

    /* Initialize private members */
    private final Color GREEN = new Color(0, 200, 51);
    private final Color BLUE = new Color(0, 51, 153);
    private final Color RED = new Color(255, 0, 0);
    private final Color SELECTED = new Color(255, 255, 150);
    private final JTextPane[][] fields;
    private boolean[][] editable;
    private final SudokuPuzzle puzzle, temp;
    private int highlighted;

    public SudokuFrame(SudokuPuzzle p) {

        /* Sets up the window components and design */
        this.puzzle = this.temp = p;
        this.highlighted = 0;
        this.initComponents();
        this.getContentPane().setBackground(new Color(180, 180, 180));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sudoku_icon.png")));
        this.setLocation(300, 100);

        /* Keep a 2-d list of the panels for easy accessing and checking */
        this.fields = new JTextPane[][]{
        {this.A1, this.A2, this.A3, this.A4, this.A5, this.A6, this.A7, this.A8, this.A9},
        {this.B1, this.B2, this.B3, this.B4, this.B5, this.B6, this.B7, this.B8, this.B9},
        {this.C1, this.C2, this.C3, this.C4, this.C5, this.C6, this.C7, this.C8, this.C9},
        {this.D1, this.D2, this.D3, this.D4, this.D5, this.D6, this.D7, this.D8, this.D9},
        {this.E1, this.E2, this.E3, this.E4, this.E5, this.E6, this.E7, this.E8, this.E9},
        {this.F1, this.F2, this.F3, this.F4, this.F5, this.F6, this.F7, this.F8, this.F9},
        {this.G1, this.G2, this.G3, this.G4, this.G5, this.G6, this.G7, this.G8, this.G9},
        {this.H1, this.H2, this.H3, this.H4, this.H5, this.H6, this.H7, this.H8, this.H9},
        {this.I1, this.I2, this.I3, this.I4, this.I5, this.I6, this.I7, this.I8, this.I9}};

        /* Keep a 2-d array of booleans to track which panels can be editable */
        this.editable = new boolean[9][9];

        /* Set up the puzzle */
        this.initializeTable();

        /* Adds the event listeners for each panel */
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextPane t = this.fields[i][j];
                boolean flag = this.editable[i][j];
                int k = i;
                int m = j;

                /* Adds the mouse listeners for each panel */
                t.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2)
                            highlight(t);
                    }
                });

                /* Adds the focus listeners for each panel */
                t.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        UIDefaults defaults = new UIDefaults();
                        defaults.put("TextPane[Enabled].backgroundPainter", SELECTED);
                        t.putClientProperty("Nimbus.Overrides", defaults);
                        t.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
                        t.setBackground(SELECTED);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        UIDefaults defaults = new UIDefaults();
                        defaults.put("TextPane[Enabled].backgroundPainter", Color.WHITE);
                        t.putClientProperty("Nimbus.Overrides", defaults);
                        t.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
                        t.setBackground(Color.WHITE);
                    }
                });


                /* Adds the key listeners for each panel */
                t.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!flag)  /* If square is uneditable, do nothing */
                            return;
                        if (!(e.getKeyChar() == '1' || e.getKeyChar() == '2' ||
                                e.getKeyChar() == '3' || e.getKeyChar() == '4' ||
                                e.getKeyChar() == '5' || e.getKeyChar() == '6' ||
                                e.getKeyChar() == '7' || e.getKeyChar() == '8' ||
                                e.getKeyChar() == '9')) {
                            t.setText("");  /* If not a valid number, delete the value in square */
                            temp.remove(k, m);
                        }

                        else {
                            if (Integer.parseInt(Character.toString(e.getKeyChar())) == highlighted)
                                t.setForeground(GREEN);
                            t.setText(Character.toString(e.getKeyChar()));
                            temp.insert(Integer.parseInt(Character.toString(e.getKeyChar())), k, m);
                        }
                    }
                    @Override
                    public void keyPressed(KeyEvent e) {/* No implementation needed */}
                    @Override
                    public void keyReleased(KeyEvent e) {/* No implementation needed */}
                });
            }
        }

        /* Asks user if they're sure when closing the window. */
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                if (WindowUtility.askYesNo("Are you sure you want to quit?",
                        "Quitting")) {
                    System.exit(0);
                }
            }
        });
        this.setVisible(true);
    }


    /**
     * Paints the lines in the window.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        super.paint(g);
        g2d.draw(new Line2D.Float(192, 91, 192, 539));
        g2d.draw(new Line2D.Float(347, 91, 347, 539));
        g2d.draw(new Line2D.Float(44, 237, 495, 237));
        g2d.draw(new Line2D.Float(44, 394, 495, 394));
    }

    /**
     * Takes the puzzle given and sets up the board in the window for the user.
     */
    protected void initializeTable() {
        int[][] board = this.puzzle.toArray();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                /* Sets the text alignment in each grid to centered */
                StyledDocument doc = this.fields[i][j].getStyledDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                doc.setParagraphAttributes(0, doc.getLength(), center, false);
                this.fields[i][j].setEditable(false);

                /* Makes the space uneditable if the number is predetermined, or editable if otherwise */
                if (board[i][j] != 0) {
                    this.fields[i][j].setForeground(Color.BLACK);
                    this.fields[i][j].setText(Integer.toString(board[i][j]));
                } else {
                    this.editable[i][j] = true;
                    this.fields[i][j].setText("");
                    this.fields[i][j].setForeground(this.BLUE);
                }
            }
        }
    }


    /**
     * Returns the current sudoku puzzle in a 2-d integer array format. Used to
     * check the puzzle for completeness.
     */
    private int[][] exportBoard() {
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    board[i][j] = Integer.parseInt(this.fields[i][j].getText());
                } catch (Exception e) {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }


    /**
     * Highlights all the numbers common with the number selected in the board
     * as green.
     */
    private void highlight(JTextPane t) {
        try {
            int k = Integer.parseInt(t.getText());
            this.highlighted = k;
            if (t.getForeground() == this.GREEN) {
                this.resetColors();
                this.highlighted = 0;
                return;
            }
            this.resetColors();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    try {
                        if (Integer.parseInt(this.fields[i][j].getText()) == k) {
                            this.fields[i][j].setForeground(this.GREEN);
                        }
                    } catch (Exception e) {/* Ignore exceptions */}
                }
            }
        } catch (Exception e) {/* Ignore exceptions */}
    }


    /**
     * Resets the color of all numbers back to its original form. Uneditable
     * numbers are reset to black, and others set to blue.
     */
    private void resetColors() {
        int[][] board = this.puzzle.toArray();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (board[i][j] != 0) {
                    this.fields[i][j].setForeground(Color.BLACK);
                    this.fields[i][j].setText(Integer.toString(board[i][j]));
                } else {
                    this.fields[i][j].setForeground(this.BLUE);
                }
            }
        }
    }


    /**
     * Resets the game back to its initial state. Called when the user requests
     * to reset the game.
     */
    private void resetGame() {
        int[][] board = this.puzzle.toArray();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0)
                    this.fields[i][j].setText(Integer.toString(board[i][j]));
                else
                    this.fields[i][j].setText("");
            }
        }
        this.resetColors();
        this.repaint();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        TimeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        A1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        A2 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        A3 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        A4 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        A5 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        A6 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        A7 = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        A8 = new javax.swing.JTextPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        A9 = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        B1 = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        B2 = new javax.swing.JTextPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        B3 = new javax.swing.JTextPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        B4 = new javax.swing.JTextPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        B5 = new javax.swing.JTextPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        B6 = new javax.swing.JTextPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        B7 = new javax.swing.JTextPane();
        jScrollPane17 = new javax.swing.JScrollPane();
        B8 = new javax.swing.JTextPane();
        jScrollPane18 = new javax.swing.JScrollPane();
        B9 = new javax.swing.JTextPane();
        jScrollPane19 = new javax.swing.JScrollPane();
        C1 = new javax.swing.JTextPane();
        jScrollPane20 = new javax.swing.JScrollPane();
        C2 = new javax.swing.JTextPane();
        jScrollPane21 = new javax.swing.JScrollPane();
        C3 = new javax.swing.JTextPane();
        jScrollPane22 = new javax.swing.JScrollPane();
        C4 = new javax.swing.JTextPane();
        jScrollPane23 = new javax.swing.JScrollPane();
        C5 = new javax.swing.JTextPane();
        jScrollPane24 = new javax.swing.JScrollPane();
        C6 = new javax.swing.JTextPane();
        jScrollPane25 = new javax.swing.JScrollPane();
        C7 = new javax.swing.JTextPane();
        jScrollPane26 = new javax.swing.JScrollPane();
        C8 = new javax.swing.JTextPane();
        jScrollPane27 = new javax.swing.JScrollPane();
        C9 = new javax.swing.JTextPane();
        jScrollPane28 = new javax.swing.JScrollPane();
        D1 = new javax.swing.JTextPane();
        jScrollPane29 = new javax.swing.JScrollPane();
        D2 = new javax.swing.JTextPane();
        jScrollPane30 = new javax.swing.JScrollPane();
        D3 = new javax.swing.JTextPane();
        jScrollPane31 = new javax.swing.JScrollPane();
        D4 = new javax.swing.JTextPane();
        jScrollPane32 = new javax.swing.JScrollPane();
        D5 = new javax.swing.JTextPane();
        jScrollPane33 = new javax.swing.JScrollPane();
        D6 = new javax.swing.JTextPane();
        jScrollPane34 = new javax.swing.JScrollPane();
        D7 = new javax.swing.JTextPane();
        jScrollPane35 = new javax.swing.JScrollPane();
        D8 = new javax.swing.JTextPane();
        jScrollPane36 = new javax.swing.JScrollPane();
        D9 = new javax.swing.JTextPane();
        jScrollPane37 = new javax.swing.JScrollPane();
        E1 = new javax.swing.JTextPane();
        jScrollPane38 = new javax.swing.JScrollPane();
        E2 = new javax.swing.JTextPane();
        jScrollPane39 = new javax.swing.JScrollPane();
        E3 = new javax.swing.JTextPane();
        jScrollPane40 = new javax.swing.JScrollPane();
        E4 = new javax.swing.JTextPane();
        jScrollPane41 = new javax.swing.JScrollPane();
        E5 = new javax.swing.JTextPane();
        jScrollPane42 = new javax.swing.JScrollPane();
        E6 = new javax.swing.JTextPane();
        jScrollPane43 = new javax.swing.JScrollPane();
        E7 = new javax.swing.JTextPane();
        jScrollPane44 = new javax.swing.JScrollPane();
        E8 = new javax.swing.JTextPane();
        jScrollPane45 = new javax.swing.JScrollPane();
        E9 = new javax.swing.JTextPane();
        jScrollPane47 = new javax.swing.JScrollPane();
        F1 = new javax.swing.JTextPane();
        jScrollPane48 = new javax.swing.JScrollPane();
        F2 = new javax.swing.JTextPane();
        jScrollPane49 = new javax.swing.JScrollPane();
        F3 = new javax.swing.JTextPane();
        jScrollPane50 = new javax.swing.JScrollPane();
        F4 = new javax.swing.JTextPane();
        jScrollPane51 = new javax.swing.JScrollPane();
        F5 = new javax.swing.JTextPane();
        jScrollPane52 = new javax.swing.JScrollPane();
        F6 = new javax.swing.JTextPane();
        jScrollPane53 = new javax.swing.JScrollPane();
        F7 = new javax.swing.JTextPane();
        jScrollPane54 = new javax.swing.JScrollPane();
        F8 = new javax.swing.JTextPane();
        jScrollPane55 = new javax.swing.JScrollPane();
        F9 = new javax.swing.JTextPane();
        jScrollPane56 = new javax.swing.JScrollPane();
        G1 = new javax.swing.JTextPane();
        jScrollPane57 = new javax.swing.JScrollPane();
        G2 = new javax.swing.JTextPane();
        jScrollPane58 = new javax.swing.JScrollPane();
        G3 = new javax.swing.JTextPane();
        jScrollPane59 = new javax.swing.JScrollPane();
        G4 = new javax.swing.JTextPane();
        jScrollPane60 = new javax.swing.JScrollPane();
        G5 = new javax.swing.JTextPane();
        jScrollPane61 = new javax.swing.JScrollPane();
        G6 = new javax.swing.JTextPane();
        jScrollPane62 = new javax.swing.JScrollPane();
        G7 = new javax.swing.JTextPane();
        jScrollPane63 = new javax.swing.JScrollPane();
        G8 = new javax.swing.JTextPane();
        jScrollPane64 = new javax.swing.JScrollPane();
        G9 = new javax.swing.JTextPane();
        jScrollPane65 = new javax.swing.JScrollPane();
        H1 = new javax.swing.JTextPane();
        jScrollPane66 = new javax.swing.JScrollPane();
        H2 = new javax.swing.JTextPane();
        jScrollPane67 = new javax.swing.JScrollPane();
        H3 = new javax.swing.JTextPane();
        jScrollPane68 = new javax.swing.JScrollPane();
        H4 = new javax.swing.JTextPane();
        jScrollPane69 = new javax.swing.JScrollPane();
        H5 = new javax.swing.JTextPane();
        jScrollPane70 = new javax.swing.JScrollPane();
        H6 = new javax.swing.JTextPane();
        jScrollPane71 = new javax.swing.JScrollPane();
        H7 = new javax.swing.JTextPane();
        jScrollPane72 = new javax.swing.JScrollPane();
        H8 = new javax.swing.JTextPane();
        jScrollPane73 = new javax.swing.JScrollPane();
        H9 = new javax.swing.JTextPane();
        jScrollPane74 = new javax.swing.JScrollPane();
        I1 = new javax.swing.JTextPane();
        jScrollPane75 = new javax.swing.JScrollPane();
        I2 = new javax.swing.JTextPane();
        jScrollPane76 = new javax.swing.JScrollPane();
        I3 = new javax.swing.JTextPane();
        jScrollPane77 = new javax.swing.JScrollPane();
        I4 = new javax.swing.JTextPane();
        jScrollPane78 = new javax.swing.JScrollPane();
        I5 = new javax.swing.JTextPane();
        jScrollPane79 = new javax.swing.JScrollPane();
        I6 = new javax.swing.JTextPane();
        jScrollPane80 = new javax.swing.JScrollPane();
        I7 = new javax.swing.JTextPane();
        jScrollPane81 = new javax.swing.JScrollPane();
        I8 = new javax.swing.JTextPane();
        jScrollPane82 = new javax.swing.JScrollPane();
        I9 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        NewGameOption = new javax.swing.JMenuItem();
        ResetGameOption = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        QuitOption = new javax.swing.JMenuItem();
        OptionsMenu = new javax.swing.JMenu();
        HelpMenu = new javax.swing.JMenu();

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setResizable(false);

        TimeLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TimeLabel.setText("Time:");

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A1.setHighlighter(null);
        A1.setSelectionColor(new java.awt.Color(240, 240, 240));
        jScrollPane1.setViewportView(A1);

        jScrollPane2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A2.setHighlighter(null);
        A2.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(A2);

        jScrollPane3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A3.setHighlighter(null);
        A3.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(A3);

        jScrollPane4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A4.setHighlighter(null);
        A4.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane4.setViewportView(A4);

        jScrollPane5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A5.setHighlighter(null);
        A5.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane5.setViewportView(A5);

        jScrollPane6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A6.setHighlighter(null);
        A6.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane6.setViewportView(A6);

        jScrollPane7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A7.setHighlighter(null);
        A7.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane7.setViewportView(A7);

        jScrollPane8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A8.setHighlighter(null);
        A8.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane8.setViewportView(A8);

        jScrollPane9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        A9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A9.setHighlighter(null);
        A9.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane9.setViewportView(A9);

        jScrollPane10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B1.setHighlighter(null);
        jScrollPane10.setViewportView(B1);

        jScrollPane11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B2.setHighlighter(null);
        jScrollPane11.setViewportView(B2);

        jScrollPane12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B3.setHighlighter(null);
        jScrollPane12.setViewportView(B3);

        jScrollPane13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B4.setHighlighter(null);
        jScrollPane13.setViewportView(B4);

        jScrollPane14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B5.setHighlighter(null);
        jScrollPane14.setViewportView(B5);

        jScrollPane15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B6.setHighlighter(null);
        jScrollPane15.setViewportView(B6);

        jScrollPane16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B7.setHighlighter(null);
        jScrollPane16.setViewportView(B7);

        jScrollPane17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B8.setHighlighter(null);
        jScrollPane17.setViewportView(B8);

        jScrollPane18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        B9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B9.setHighlighter(null);
        jScrollPane18.setViewportView(B9);

        jScrollPane19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C1.setHighlighter(null);
        jScrollPane19.setViewportView(C1);

        jScrollPane20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C2.setHighlighter(null);
        jScrollPane20.setViewportView(C2);

        jScrollPane21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C3.setHighlighter(null);
        jScrollPane21.setViewportView(C3);

        jScrollPane22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C4.setHighlighter(null);
        jScrollPane22.setViewportView(C4);

        jScrollPane23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C5.setHighlighter(null);
        jScrollPane23.setViewportView(C5);

        jScrollPane24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C6.setHighlighter(null);
        jScrollPane24.setViewportView(C6);

        jScrollPane25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C7.setHighlighter(null);
        jScrollPane25.setViewportView(C7);

        jScrollPane26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C8.setHighlighter(null);
        jScrollPane26.setViewportView(C8);

        jScrollPane27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        C9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C9.setHighlighter(null);
        jScrollPane27.setViewportView(C9);

        jScrollPane28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D1.setHighlighter(null);
        jScrollPane28.setViewportView(D1);

        jScrollPane29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D2.setHighlighter(null);
        jScrollPane29.setViewportView(D2);

        jScrollPane30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D3.setHighlighter(null);
        jScrollPane30.setViewportView(D3);

        jScrollPane31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D4.setHighlighter(null);
        jScrollPane31.setViewportView(D4);

        jScrollPane32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D5.setHighlighter(null);
        jScrollPane32.setViewportView(D5);

        jScrollPane33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D6.setHighlighter(null);
        jScrollPane33.setViewportView(D6);

        jScrollPane34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D7.setHighlighter(null);
        jScrollPane34.setViewportView(D7);

        jScrollPane35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D8.setHighlighter(null);
        jScrollPane35.setViewportView(D8);

        jScrollPane36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        D9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D9.setHighlighter(null);
        jScrollPane36.setViewportView(D9);

        jScrollPane37.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E1.setHighlighter(null);
        jScrollPane37.setViewportView(E1);

        jScrollPane38.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E2.setHighlighter(null);
        jScrollPane38.setViewportView(E2);

        jScrollPane39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E3.setHighlighter(null);
        jScrollPane39.setViewportView(E3);

        jScrollPane40.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E4.setHighlighter(null);
        jScrollPane40.setViewportView(E4);

        jScrollPane41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E5.setHighlighter(null);
        jScrollPane41.setViewportView(E5);

        jScrollPane42.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E6.setHighlighter(null);
        jScrollPane42.setViewportView(E6);

        jScrollPane43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E7.setHighlighter(null);
        jScrollPane43.setViewportView(E7);

        jScrollPane44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E8.setHighlighter(null);
        jScrollPane44.setViewportView(E8);

        jScrollPane45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        E9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E9.setHighlighter(null);
        jScrollPane45.setViewportView(E9);

        jScrollPane47.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F1.setHighlighter(null);
        jScrollPane47.setViewportView(F1);

        jScrollPane48.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F2.setHighlighter(null);
        jScrollPane48.setViewportView(F2);

        jScrollPane49.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F3.setHighlighter(null);
        jScrollPane49.setViewportView(F3);

        jScrollPane50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F4.setHighlighter(null);
        jScrollPane50.setViewportView(F4);

        jScrollPane51.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F5.setHighlighter(null);
        jScrollPane51.setViewportView(F5);

        jScrollPane52.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F6.setHighlighter(null);
        jScrollPane52.setViewportView(F6);

        jScrollPane53.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F7.setHighlighter(null);
        jScrollPane53.setViewportView(F7);

        jScrollPane54.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F8.setHighlighter(null);
        jScrollPane54.setViewportView(F8);

        jScrollPane55.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        F9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F9.setHighlighter(null);
        jScrollPane55.setViewportView(F9);

        jScrollPane56.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G1.setHighlighter(null);
        jScrollPane56.setViewportView(G1);

        jScrollPane57.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G2.setHighlighter(null);
        jScrollPane57.setViewportView(G2);

        jScrollPane58.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G3.setHighlighter(null);
        jScrollPane58.setViewportView(G3);

        jScrollPane59.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G4.setHighlighter(null);
        jScrollPane59.setViewportView(G4);

        jScrollPane60.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G5.setHighlighter(null);
        jScrollPane60.setViewportView(G5);

        jScrollPane61.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G6.setHighlighter(null);
        jScrollPane61.setViewportView(G6);

        jScrollPane62.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G7.setHighlighter(null);
        jScrollPane62.setViewportView(G7);

        jScrollPane63.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G8.setHighlighter(null);
        jScrollPane63.setViewportView(G8);

        jScrollPane64.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        G9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G9.setHighlighter(null);
        jScrollPane64.setViewportView(G9);

        jScrollPane65.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H1.setHighlighter(null);
        jScrollPane65.setViewportView(H1);

        jScrollPane66.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H2.setHighlighter(null);
        jScrollPane66.setViewportView(H2);

        jScrollPane67.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H3.setHighlighter(null);
        jScrollPane67.setViewportView(H3);

        jScrollPane68.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H4.setHighlighter(null);
        jScrollPane68.setViewportView(H4);

        jScrollPane69.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H5.setHighlighter(null);
        jScrollPane69.setViewportView(H5);

        jScrollPane70.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H6.setHighlighter(null);
        jScrollPane70.setViewportView(H6);

        jScrollPane71.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H7.setHighlighter(null);
        jScrollPane71.setViewportView(H7);

        jScrollPane72.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H8.setHighlighter(null);
        jScrollPane72.setViewportView(H8);

        jScrollPane73.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        H9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H9.setHighlighter(null);
        jScrollPane73.setViewportView(H9);

        jScrollPane74.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I1.setHighlighter(null);
        jScrollPane74.setViewportView(I1);

        jScrollPane75.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I2.setHighlighter(null);
        jScrollPane75.setViewportView(I2);

        jScrollPane76.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I3.setHighlighter(null);
        jScrollPane76.setViewportView(I3);

        jScrollPane77.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I4.setHighlighter(null);
        jScrollPane77.setViewportView(I4);

        jScrollPane78.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I5.setHighlighter(null);
        jScrollPane78.setViewportView(I5);

        jScrollPane79.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I6.setHighlighter(null);
        jScrollPane79.setViewportView(I6);

        jScrollPane80.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I7.setHighlighter(null);
        jScrollPane80.setViewportView(I7);

        jScrollPane81.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I8.setHighlighter(null);
        jScrollPane81.setViewportView(I8);

        jScrollPane82.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        I9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I9.setHighlighter(null);
        jScrollPane82.setViewportView(I9);

        jButton1.setText("Check");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        FileMenu.setText("File");

        NewGameOption.setText("New Game");
        NewGameOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameOptionActionPerformed(evt);
            }
        });
        FileMenu.add(NewGameOption);

        ResetGameOption.setText("Reset Game");
        ResetGameOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetGameOptionActionPerformed(evt);
            }
        });
        FileMenu.add(ResetGameOption);
        FileMenu.add(jSeparator1);

        QuitOption.setText("Quit");
        QuitOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitOptionActionPerformed(evt);
            }
        });
        FileMenu.add(QuitOption);

        jMenuBar1.add(FileMenu);

        OptionsMenu.setText("Options");
        jMenuBar1.add(OptionsMenu);

        HelpMenu.setText("Help");
        jMenuBar1.add(HelpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jScrollPane47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane48, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane49, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane50, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane51, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane52, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane53, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane54, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane55, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane37, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane38, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane39, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane40, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane41, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane42, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane43, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane44, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane45, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane35, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane36, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jScrollPane56, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane57, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane58, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane59, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane60, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane61, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane62, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane63, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane64, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane65, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane66, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane67, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane68, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane69, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane70, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane71, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane72, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane73, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane74, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane75, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane76, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane77, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane78, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane79, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane80, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane81, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane82, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TimeLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TimeLabel)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane37, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane39, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane38, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane43, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane45, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane44, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane40, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane42, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane41, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane48, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane49, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane54, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane55, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane53, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane50, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane51, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane52, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane56, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane57, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane58, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane59, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane60, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane61, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane62, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane64, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane63, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane65, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane66, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane67, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane68, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane69, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane70, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane73, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane71, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane72, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane74, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane75, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane79, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane77, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane78, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane80, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane81, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane82, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane76, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SudokuPuzzle e = new SudokuPuzzle();
        e.setArray(exportBoard());
        if (e.isComplete())
            this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void NewGameOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameOptionActionPerformed
        if (WindowUtility.askYesNo("Are you sure you want to start a new game?", "New Game")) {
            WindowUtility.displayInfo("Not implemented yet...", "Note!");
        }
    }//GEN-LAST:event_NewGameOptionActionPerformed
    private void ResetGameOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetGameOptionActionPerformed
        if (WindowUtility.askYesNo("Are you sure you want to reset the game?", "Resetting"))
            this.resetGame();
    }//GEN-LAST:event_ResetGameOptionActionPerformed
    private void QuitOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitOptionActionPerformed
        if (WindowUtility.askYesNo("Are you sure you want to exit?", "Exiting"))
            System.exit(0);
    }//GEN-LAST:event_QuitOptionActionPerformed

    /* UI component variable declarations */
    //<editor-fold defaultstate="collapsed" desc=" Component declarations (optional) ">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane A1;
    private javax.swing.JTextPane A2;
    private javax.swing.JTextPane A3;
    private javax.swing.JTextPane A4;
    private javax.swing.JTextPane A5;
    private javax.swing.JTextPane A6;
    private javax.swing.JTextPane A7;
    private javax.swing.JTextPane A8;
    private javax.swing.JTextPane A9;
    private javax.swing.JTextPane B1;
    private javax.swing.JTextPane B2;
    private javax.swing.JTextPane B3;
    private javax.swing.JTextPane B4;
    private javax.swing.JTextPane B5;
    private javax.swing.JTextPane B6;
    private javax.swing.JTextPane B7;
    private javax.swing.JTextPane B8;
    private javax.swing.JTextPane B9;
    private javax.swing.JTextPane C1;
    private javax.swing.JTextPane C2;
    private javax.swing.JTextPane C3;
    private javax.swing.JTextPane C4;
    private javax.swing.JTextPane C5;
    private javax.swing.JTextPane C6;
    private javax.swing.JTextPane C7;
    private javax.swing.JTextPane C8;
    private javax.swing.JTextPane C9;
    private javax.swing.JTextPane D1;
    private javax.swing.JTextPane D2;
    private javax.swing.JTextPane D3;
    private javax.swing.JTextPane D4;
    private javax.swing.JTextPane D5;
    private javax.swing.JTextPane D6;
    private javax.swing.JTextPane D7;
    private javax.swing.JTextPane D8;
    private javax.swing.JTextPane D9;
    private javax.swing.JTextPane E1;
    private javax.swing.JTextPane E2;
    private javax.swing.JTextPane E3;
    private javax.swing.JTextPane E4;
    private javax.swing.JTextPane E5;
    private javax.swing.JTextPane E6;
    private javax.swing.JTextPane E7;
    private javax.swing.JTextPane E8;
    private javax.swing.JTextPane E9;
    private javax.swing.JTextPane F1;
    private javax.swing.JTextPane F2;
    private javax.swing.JTextPane F3;
    private javax.swing.JTextPane F4;
    private javax.swing.JTextPane F5;
    private javax.swing.JTextPane F6;
    private javax.swing.JTextPane F7;
    private javax.swing.JTextPane F8;
    private javax.swing.JTextPane F9;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JTextPane G1;
    private javax.swing.JTextPane G2;
    private javax.swing.JTextPane G3;
    private javax.swing.JTextPane G4;
    private javax.swing.JTextPane G5;
    private javax.swing.JTextPane G6;
    private javax.swing.JTextPane G7;
    private javax.swing.JTextPane G8;
    private javax.swing.JTextPane G9;
    private javax.swing.JTextPane H1;
    private javax.swing.JTextPane H2;
    private javax.swing.JTextPane H3;
    private javax.swing.JTextPane H4;
    private javax.swing.JTextPane H5;
    private javax.swing.JTextPane H6;
    private javax.swing.JTextPane H7;
    private javax.swing.JTextPane H8;
    private javax.swing.JTextPane H9;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JTextPane I1;
    private javax.swing.JTextPane I2;
    private javax.swing.JTextPane I3;
    private javax.swing.JTextPane I4;
    private javax.swing.JTextPane I5;
    private javax.swing.JTextPane I6;
    private javax.swing.JTextPane I7;
    private javax.swing.JTextPane I8;
    private javax.swing.JTextPane I9;
    private javax.swing.JMenuItem NewGameOption;
    private javax.swing.JMenu OptionsMenu;
    private javax.swing.JMenuItem QuitOption;
    private javax.swing.JMenuItem ResetGameOption;
    private javax.swing.JLabel TimeLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane37;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane40;
    private javax.swing.JScrollPane jScrollPane41;
    private javax.swing.JScrollPane jScrollPane42;
    private javax.swing.JScrollPane jScrollPane43;
    private javax.swing.JScrollPane jScrollPane44;
    private javax.swing.JScrollPane jScrollPane45;
    private javax.swing.JScrollPane jScrollPane47;
    private javax.swing.JScrollPane jScrollPane48;
    private javax.swing.JScrollPane jScrollPane49;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane50;
    private javax.swing.JScrollPane jScrollPane51;
    private javax.swing.JScrollPane jScrollPane52;
    private javax.swing.JScrollPane jScrollPane53;
    private javax.swing.JScrollPane jScrollPane54;
    private javax.swing.JScrollPane jScrollPane55;
    private javax.swing.JScrollPane jScrollPane56;
    private javax.swing.JScrollPane jScrollPane57;
    private javax.swing.JScrollPane jScrollPane58;
    private javax.swing.JScrollPane jScrollPane59;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane60;
    private javax.swing.JScrollPane jScrollPane61;
    private javax.swing.JScrollPane jScrollPane62;
    private javax.swing.JScrollPane jScrollPane63;
    private javax.swing.JScrollPane jScrollPane64;
    private javax.swing.JScrollPane jScrollPane65;
    private javax.swing.JScrollPane jScrollPane66;
    private javax.swing.JScrollPane jScrollPane67;
    private javax.swing.JScrollPane jScrollPane68;
    private javax.swing.JScrollPane jScrollPane69;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane70;
    private javax.swing.JScrollPane jScrollPane71;
    private javax.swing.JScrollPane jScrollPane72;
    private javax.swing.JScrollPane jScrollPane73;
    private javax.swing.JScrollPane jScrollPane74;
    private javax.swing.JScrollPane jScrollPane75;
    private javax.swing.JScrollPane jScrollPane76;
    private javax.swing.JScrollPane jScrollPane77;
    private javax.swing.JScrollPane jScrollPane78;
    private javax.swing.JScrollPane jScrollPane79;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane80;
    private javax.swing.JScrollPane jScrollPane81;
    private javax.swing.JScrollPane jScrollPane82;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables
//</editor-fold>
}
