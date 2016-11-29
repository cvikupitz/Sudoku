/**
 * PuzzlesFrameFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that contains a GUI for editing a custom puzzle. User can input numbers,
 * save the puzzle, rename the puzzle, clear the puzzle, and quit out.
 */
package sudoku;


/* Imports */
import java.awt.BasicStroke;
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
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.UIDefaults;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class PuzzleEditorFrame extends JFrame {

    /* Declare private members */
    private final JTextPane[][] fields;
    private final JTextPane[] legalBoxes;
    private String title;
    private int highlighted;
    private boolean saved;
    private SudokuPuzzle puzzle;

    /* Default constructor */
    public PuzzleEditorFrame(SudokuPuzzle p, String t, int x, int y) {

        /* Initialize components */
        this.puzzle = p;
        this.highlighted = 0;
        this.title = t;
        this.saved = true;
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sudoku_icon.png")));
        this.setTitle(this.title);
        this.setLocation(x, y);

        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane[Enabled].backgroundPainter", GUIColors.BACKGROUND);
        this.statusField.putClientProperty("Nimbus.Overrides", defaults);
        this.statusField.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        this.statusField.setBackground(GUIColors.BACKGROUND);

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

        /* Keep a list of the panels displaying the legal moves for easy accessing */
        this.legalBoxes = new JTextPane[]{
            this.legalOne, this.legalTwo, this.legalThree,
            this.legalFour, this.legalFive, this.legalSix,
            this.legalSeven, this.legalEight, this.legalNine};
        this.initializeTable();

        /* Add the event listeners for each panel */
        for (int i = 0; i < 9; i++) {
            this.legalBoxes[i].setEditable(false);
            for (int j = 0; j < 9; j++) {
                JTextPane pane = this.fields[i][j];
                int m = i;
                int n = j;

                /* Adds the mouse listeners for each panel */
                pane.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        updateLegalMoves(m, n);
                        if (e.getClickCount() == 2)
                            highlight(pane);
                    }
                });

                /* Adds the focus listeners for each panel */
                pane.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        UIDefaults defaults = new UIDefaults();
                        defaults.put("TextPane[Enabled].backgroundPainter", GUIColors.SELECTED);
                        pane.putClientProperty("Nimbus.Overrides", defaults);
                        pane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
                        pane.setBackground(GUIColors.SELECTED);
                        updateLegalMoves(m, n);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        UIDefaults defaults = new UIDefaults();
                        defaults.put("TextPane[Enabled].backgroundPainter", GUIColors.WHITE);
                        pane.putClientProperty("Nimbus.Overrides", defaults);
                        pane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
                        pane.setBackground(GUIColors.WHITE);
                    }
                });

                /* Adds the key listeners for each panel */
                pane.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!(e.getKeyChar() == '1' || e.getKeyChar() == '2' ||
                                e.getKeyChar() == '3' || e.getKeyChar() == '4' ||
                                e.getKeyChar() == '5' || e.getKeyChar() == '6' ||
                                e.getKeyChar() == '7' || e.getKeyChar() == '8' ||
                                e.getKeyChar() == '9')) {
                            pane.setText("");  /* If not a valid number, delete the value in square */
                            puzzle.remove(m, n);
                        } else {
                            int x = Integer.parseInt(Character.toString(e.getKeyChar()));
                            if (x == highlighted)
                                pane.setForeground(GUIColors.GREEN);
                            else
                                pane.setForeground(GUIColors.BLACK);
                            pane.setText(Integer.toString(x));
                            if (!puzzle.insert(x, m, n))
                                pane.setForeground(GUIColors.RED);
                        }
                        updateStatus();
                        setSaved(false);
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
                    FileUtility.saveBestTimes();
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
        g2d.drawRect(10, 30, 435, 440);
        g2d.draw(new Line2D.Float(155, 30, 155, 467));
        g2d.draw(new Line2D.Float(299, 30, 299, 467));
        g2d.draw(new Line2D.Float(10, 176, 443, 176));
        g2d.draw(new Line2D.Float(10, 322, 443, 322));
    }


    /**
     * Takes the puzzle given and sets up the board in the window for the user.
     */
    private void initializeTable() {

        this.highlighted = 0;
        int k = 0;
        String s = this.puzzle.initialPuzzleState();
        this.puzzle.hardReset();
        for (int i = 0; i < 9; i++) {

            /* Sets the text alignment in each grid to centered */
            StyledDocument l_doc = this.legalBoxes[i].getStyledDocument();
            SimpleAttributeSet l_center = new SimpleAttributeSet();
            StyleConstants.setAlignment(l_center, StyleConstants.ALIGN_CENTER);
            l_doc.setParagraphAttributes(0, l_doc.getLength(), l_center, false);

            for (int j = 0; j < 9; j++) {

                /* Sets the text alignment in each grid to centered */
                StyledDocument f_doc = this.fields[i][j].getStyledDocument();
                SimpleAttributeSet f_center = new SimpleAttributeSet();
                StyleConstants.setAlignment(f_center, StyleConstants.ALIGN_CENTER);
                f_doc.setParagraphAttributes(0, f_doc.getLength(), f_center, false);
                this.fields[i][j].setEditable(false);
                int val = Integer.parseInt(Character.toString(s.charAt(k)));

                if (!this.puzzle.insert(val, i, j))
                    this.fields[i][j].setForeground(GUIColors.RED);
                else
                    this.fields[i][j].setForeground(GUIColors.BLACK);

                /* Makes the space uneditable if the number is predetermined, or editable if otherwise */
                if (s.charAt(k) != '0')
                    this.fields[i][j].setText(Character.toString(s.charAt(k)));
                k++;
            }
        }
        this.updateStatus();
    }


    /**
     * FIXME
     */
    private void setSaved(boolean flag) {
        this.saved = flag;
        if (this.saved)
            this.setTitle(this.title);
        else
            this.setTitle(this.title + "*");
    }


    /**
     * FIXME
     */
    private void updateLegalMoves(int i, int j) {
        boolean[] legalMoves = this.puzzle.getLegalMoves(i, j);
        for (int k = 0; k < 9; k++) {
            if (legalMoves[k])
                this.legalBoxes[k].setText(Integer.toString(k + 1));
            else
                this.legalBoxes[k].setText("");
        }
    }


    /**
     * FIXME
     */
    private void updateStatus() {
        int i = this.puzzle.getNumberFilled();
        int j = (int)(((float)i / 81) * 100);
        this.statusField.setText(String.format("Tiles Filled: %d/81 (%d%%)", i, j));
    }


    /**
     * Highlights all the numbers common with the number selected in the board
     * as green.
     */
    private void highlight(JTextPane t) {
        try {
            int k = Integer.parseInt(t.getText());
            if (t.getForeground() == GUIColors.GREEN || this.highlighted == k) {
                this.resetColors();
                this.highlighted = 0;
                return;
            }
            this.highlighted = k;
            this.resetColors();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    try {
                        if (Integer.parseInt(this.fields[i][j].getText()) == this.highlighted)
                            if (this.fields[i][j].getForeground() != GUIColors.RED)
                                this.fields[i][j].setForeground(GUIColors.GREEN);
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (this.fields[i][j].getForeground() != GUIColors.RED)
                    this.fields[i][j].setForeground(GUIColors.BLACK);
        }
    }


    /***/
    private void clear() {
        if (WindowUtility.askYesNo("Are you sure you want to clear the puzzle?", "Warning!")) {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    this.fields[i][j].setText("");
            this.puzzle.hardReset();
            this.updateStatus();
            this.setSaved(false);
        }
    }


    /***/
    private void save() {
        int i = this.puzzle.getNumberFilled();
        if (45 <= i)
            this.puzzle.setDifficulty(1);
        else if (39 <= i && i < 44)
            this.puzzle.setDifficulty(2);
        else if (32 <= i && i < 38)
            this.puzzle.setDifficulty(3);
        else if (26 <= i && i < 31)
            this.puzzle.setDifficulty(4);
        else
            this.puzzle.setDifficulty(5);
        this.puzzle.setInitialPuzzleState(this.puzzle.currentPuzzleState());
        FileUtility.saveGame(puzzle, puzzle.getDifficulty(),
                FileUtility.MY_PUZZLES_PATH + this.title + ".txt");
        this.setSaved(true);
    }


    /***/
    private void saveAs() {
        String new_name = WindowUtility.getEntry("What would you like to rename this puzzle as?");
        String old = this.title;
        if (new_name == null)
            return;
        if (!FileUtility.fileNameValid(new_name)) {
            WindowUtility.errorMessage("You entered an illegal name.", "Error!");
            return;
        }
        if (!FileUtility.nameIsUnique(new_name + ".txt", FileUtility.MY_PUZZLES_PATH)) {
            WindowUtility.errorMessage("There already exists a puzzle with that name.", "Error");
            return;
        }
        if (!FileUtility.copyFile(FileUtility.MY_PUZZLES_PATH + this.title + ".txt",
                FileUtility.MY_PUZZLES_PATH + new_name + ".txt")) {
            WindowUtility.errorMessage("Failed to save the puzzle.", "Error!");
            return;
        }
        File file = FileUtility.getFile(old + ".txt", FileUtility.MY_PUZZLES_PATH);
        if (!file.delete()) {
            WindowUtility.errorMessage("Failed to delete the old file.", "Error!");
            return;
        }
        this.title = new_name;
        this.save();
        this.setSaved(true);
    }


    /***/
    private void quit() {
        if (!this.saved)
            if (!WindowUtility.askYesNo("You have unsaved changes.\nAre you sure you want to quit?", "Warning!"))
                return;
        PuzzlesFrame f = new PuzzlesFrame(this.getX(), this.getY());
        this.dispose();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane95 = new javax.swing.JScrollPane();
        B8 = new javax.swing.JTextPane();
        jScrollPane96 = new javax.swing.JScrollPane();
        B9 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        A3 = new javax.swing.JTextPane();
        jScrollPane97 = new javax.swing.JScrollPane();
        C8 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        B1 = new javax.swing.JTextPane();
        jScrollPane98 = new javax.swing.JScrollPane();
        C7 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        B2 = new javax.swing.JTextPane();
        jScrollPane99 = new javax.swing.JScrollPane();
        C9 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        B3 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        C2 = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        C1 = new javax.swing.JTextPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        C3 = new javax.swing.JTextPane();
        jScrollPane82 = new javax.swing.JScrollPane();
        A4 = new javax.swing.JTextPane();
        jScrollPane83 = new javax.swing.JScrollPane();
        A5 = new javax.swing.JTextPane();
        jScrollPane84 = new javax.swing.JScrollPane();
        A6 = new javax.swing.JTextPane();
        jScrollPane85 = new javax.swing.JScrollPane();
        B4 = new javax.swing.JTextPane();
        jScrollPane86 = new javax.swing.JScrollPane();
        B5 = new javax.swing.JTextPane();
        jScrollPane87 = new javax.swing.JScrollPane();
        B6 = new javax.swing.JTextPane();
        jScrollPane88 = new javax.swing.JScrollPane();
        C5 = new javax.swing.JTextPane();
        jScrollPane89 = new javax.swing.JScrollPane();
        C4 = new javax.swing.JTextPane();
        jScrollPane90 = new javax.swing.JScrollPane();
        C6 = new javax.swing.JTextPane();
        jScrollPane91 = new javax.swing.JScrollPane();
        A7 = new javax.swing.JTextPane();
        jScrollPane92 = new javax.swing.JScrollPane();
        A8 = new javax.swing.JTextPane();
        jScrollPane93 = new javax.swing.JScrollPane();
        A9 = new javax.swing.JTextPane();
        jScrollPane94 = new javax.swing.JScrollPane();
        B7 = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        A1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        A2 = new javax.swing.JTextPane();
        jScrollPane115 = new javax.swing.JScrollPane();
        F2 = new javax.swing.JTextPane();
        jScrollPane116 = new javax.swing.JScrollPane();
        D6 = new javax.swing.JTextPane();
        jScrollPane117 = new javax.swing.JScrollPane();
        E4 = new javax.swing.JTextPane();
        jScrollPane118 = new javax.swing.JScrollPane();
        E5 = new javax.swing.JTextPane();
        jScrollPane119 = new javax.swing.JScrollPane();
        E6 = new javax.swing.JTextPane();
        jScrollPane100 = new javax.swing.JScrollPane();
        D9 = new javax.swing.JTextPane();
        jScrollPane101 = new javax.swing.JScrollPane();
        E7 = new javax.swing.JTextPane();
        jScrollPane102 = new javax.swing.JScrollPane();
        E8 = new javax.swing.JTextPane();
        jScrollPane103 = new javax.swing.JScrollPane();
        E9 = new javax.swing.JTextPane();
        jScrollPane120 = new javax.swing.JScrollPane();
        F1 = new javax.swing.JTextPane();
        jScrollPane104 = new javax.swing.JScrollPane();
        F8 = new javax.swing.JTextPane();
        jScrollPane121 = new javax.swing.JScrollPane();
        F3 = new javax.swing.JTextPane();
        jScrollPane122 = new javax.swing.JScrollPane();
        F5 = new javax.swing.JTextPane();
        jScrollPane123 = new javax.swing.JScrollPane();
        F4 = new javax.swing.JTextPane();
        jScrollPane124 = new javax.swing.JScrollPane();
        F6 = new javax.swing.JTextPane();
        jScrollPane105 = new javax.swing.JScrollPane();
        F7 = new javax.swing.JTextPane();
        jScrollPane106 = new javax.swing.JScrollPane();
        F9 = new javax.swing.JTextPane();
        jScrollPane107 = new javax.swing.JScrollPane();
        D1 = new javax.swing.JTextPane();
        jScrollPane108 = new javax.swing.JScrollPane();
        D2 = new javax.swing.JTextPane();
        jScrollPane125 = new javax.swing.JScrollPane();
        D7 = new javax.swing.JTextPane();
        jScrollPane109 = new javax.swing.JScrollPane();
        D4 = new javax.swing.JTextPane();
        jScrollPane126 = new javax.swing.JScrollPane();
        D8 = new javax.swing.JTextPane();
        jScrollPane110 = new javax.swing.JScrollPane();
        D3 = new javax.swing.JTextPane();
        jScrollPane111 = new javax.swing.JScrollPane();
        E1 = new javax.swing.JTextPane();
        jScrollPane112 = new javax.swing.JScrollPane();
        E2 = new javax.swing.JTextPane();
        jScrollPane113 = new javax.swing.JScrollPane();
        E3 = new javax.swing.JTextPane();
        jScrollPane114 = new javax.swing.JScrollPane();
        D5 = new javax.swing.JTextPane();
        jScrollPane135 = new javax.swing.JScrollPane();
        I3 = new javax.swing.JTextPane();
        jScrollPane136 = new javax.swing.JScrollPane();
        G9 = new javax.swing.JTextPane();
        jScrollPane137 = new javax.swing.JScrollPane();
        I5 = new javax.swing.JTextPane();
        jScrollPane138 = new javax.swing.JScrollPane();
        H7 = new javax.swing.JTextPane();
        jScrollPane139 = new javax.swing.JScrollPane();
        H8 = new javax.swing.JTextPane();
        jScrollPane140 = new javax.swing.JScrollPane();
        I4 = new javax.swing.JTextPane();
        jScrollPane141 = new javax.swing.JScrollPane();
        I6 = new javax.swing.JTextPane();
        jScrollPane142 = new javax.swing.JScrollPane();
        H9 = new javax.swing.JTextPane();
        jScrollPane143 = new javax.swing.JScrollPane();
        G7 = new javax.swing.JTextPane();
        jScrollPane144 = new javax.swing.JScrollPane();
        I8 = new javax.swing.JTextPane();
        jScrollPane145 = new javax.swing.JScrollPane();
        G8 = new javax.swing.JTextPane();
        jScrollPane146 = new javax.swing.JScrollPane();
        I7 = new javax.swing.JTextPane();
        jScrollPane147 = new javax.swing.JScrollPane();
        I9 = new javax.swing.JTextPane();
        jScrollPane148 = new javax.swing.JScrollPane();
        G1 = new javax.swing.JTextPane();
        jScrollPane127 = new javax.swing.JScrollPane();
        H3 = new javax.swing.JTextPane();
        jScrollPane149 = new javax.swing.JScrollPane();
        G2 = new javax.swing.JTextPane();
        jScrollPane128 = new javax.swing.JScrollPane();
        G5 = new javax.swing.JTextPane();
        jScrollPane129 = new javax.swing.JScrollPane();
        I2 = new javax.swing.JTextPane();
        jScrollPane150 = new javax.swing.JScrollPane();
        G4 = new javax.swing.JTextPane();
        jScrollPane151 = new javax.swing.JScrollPane();
        G3 = new javax.swing.JTextPane();
        jScrollPane130 = new javax.swing.JScrollPane();
        G6 = new javax.swing.JTextPane();
        jScrollPane152 = new javax.swing.JScrollPane();
        H1 = new javax.swing.JTextPane();
        jScrollPane131 = new javax.swing.JScrollPane();
        H4 = new javax.swing.JTextPane();
        jScrollPane153 = new javax.swing.JScrollPane();
        H2 = new javax.swing.JTextPane();
        jScrollPane132 = new javax.swing.JScrollPane();
        H5 = new javax.swing.JTextPane();
        jScrollPane133 = new javax.swing.JScrollPane();
        H6 = new javax.swing.JTextPane();
        jScrollPane134 = new javax.swing.JScrollPane();
        I1 = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        legalOne = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        legalTwo = new javax.swing.JTextPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        legalThree = new javax.swing.JTextPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        legalFour = new javax.swing.JTextPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        legalFive = new javax.swing.JTextPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        legalSix = new javax.swing.JTextPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        legalEight = new javax.swing.JTextPane();
        jScrollPane17 = new javax.swing.JScrollPane();
        legalSeven = new javax.swing.JTextPane();
        jScrollPane18 = new javax.swing.JScrollPane();
        legalNine = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        statusField = new javax.swing.JTextPane();
        saveButton = new javax.swing.JButton();
        renameButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(6, 31));

        jPanel2.setBackground(new java.awt.Color(180, 180, 180));

        B8.setEditable(false);
        B8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B8.setHighlighter(null);
        jScrollPane95.setViewportView(B8);

        B9.setEditable(false);
        B9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B9.setHighlighter(null);
        jScrollPane96.setViewportView(B9);

        A3.setEditable(false);
        A3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A3.setHighlighter(null);
        jScrollPane3.setViewportView(A3);

        C8.setEditable(false);
        C8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C8.setHighlighter(null);
        jScrollPane97.setViewportView(C8);

        B1.setEditable(false);
        B1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B1.setHighlighter(null);
        jScrollPane4.setViewportView(B1);

        C7.setEditable(false);
        C7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C7.setHighlighter(null);
        jScrollPane98.setViewportView(C7);

        B2.setEditable(false);
        B2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B2.setHighlighter(null);
        jScrollPane5.setViewportView(B2);

        C9.setEditable(false);
        C9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C9.setHighlighter(null);
        jScrollPane99.setViewportView(C9);

        B3.setEditable(false);
        B3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B3.setHighlighter(null);
        jScrollPane6.setViewportView(B3);

        C2.setEditable(false);
        C2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C2.setHighlighter(null);
        jScrollPane7.setViewportView(C2);

        C1.setEditable(false);
        C1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C1.setHighlighter(null);
        jScrollPane8.setViewportView(C1);

        C3.setEditable(false);
        C3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C3.setHighlighter(null);
        jScrollPane9.setViewportView(C3);

        A4.setEditable(false);
        A4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A4.setHighlighter(null);
        jScrollPane82.setViewportView(A4);

        A5.setEditable(false);
        A5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A5.setHighlighter(null);
        jScrollPane83.setViewportView(A5);

        A6.setEditable(false);
        A6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A6.setHighlighter(null);
        jScrollPane84.setViewportView(A6);

        B4.setEditable(false);
        B4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B4.setHighlighter(null);
        jScrollPane85.setViewportView(B4);

        B5.setEditable(false);
        B5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B5.setHighlighter(null);
        jScrollPane86.setViewportView(B5);

        B6.setEditable(false);
        B6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B6.setHighlighter(null);
        jScrollPane87.setViewportView(B6);

        C5.setEditable(false);
        C5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C5.setHighlighter(null);
        jScrollPane88.setViewportView(C5);

        C4.setEditable(false);
        C4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C4.setHighlighter(null);
        jScrollPane89.setViewportView(C4);

        C6.setEditable(false);
        C6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        C6.setHighlighter(null);
        jScrollPane90.setViewportView(C6);

        A7.setEditable(false);
        A7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A7.setHighlighter(null);
        jScrollPane91.setViewportView(A7);

        A8.setEditable(false);
        A8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A8.setHighlighter(null);
        jScrollPane92.setViewportView(A8);

        A9.setEditable(false);
        A9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A9.setHighlighter(null);
        jScrollPane93.setViewportView(A9);

        B7.setEditable(false);
        B7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        B7.setHighlighter(null);
        jScrollPane94.setViewportView(B7);

        A1.setEditable(false);
        A1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A1.setHighlighter(null);
        jScrollPane1.setViewportView(A1);

        A2.setEditable(false);
        A2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        A2.setHighlighter(null);
        jScrollPane2.setViewportView(A2);

        F2.setEditable(false);
        F2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F2.setHighlighter(null);
        jScrollPane115.setViewportView(F2);

        D6.setEditable(false);
        D6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D6.setHighlighter(null);
        jScrollPane116.setViewportView(D6);

        E4.setEditable(false);
        E4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E4.setHighlighter(null);
        jScrollPane117.setViewportView(E4);

        E5.setEditable(false);
        E5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E5.setHighlighter(null);
        jScrollPane118.setViewportView(E5);

        E6.setEditable(false);
        E6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E6.setHighlighter(null);
        jScrollPane119.setViewportView(E6);

        D9.setEditable(false);
        D9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D9.setHighlighter(null);
        jScrollPane100.setViewportView(D9);

        E7.setEditable(false);
        E7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E7.setHighlighter(null);
        jScrollPane101.setViewportView(E7);

        E8.setEditable(false);
        E8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E8.setHighlighter(null);
        jScrollPane102.setViewportView(E8);

        E9.setEditable(false);
        E9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E9.setHighlighter(null);
        jScrollPane103.setViewportView(E9);

        F1.setEditable(false);
        F1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F1.setHighlighter(null);
        jScrollPane120.setViewportView(F1);

        F8.setEditable(false);
        F8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F8.setHighlighter(null);
        jScrollPane104.setViewportView(F8);

        F3.setEditable(false);
        F3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F3.setHighlighter(null);
        jScrollPane121.setViewportView(F3);

        F5.setEditable(false);
        F5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F5.setHighlighter(null);
        jScrollPane122.setViewportView(F5);

        F4.setEditable(false);
        F4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F4.setHighlighter(null);
        jScrollPane123.setViewportView(F4);

        F6.setEditable(false);
        F6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F6.setHighlighter(null);
        jScrollPane124.setViewportView(F6);

        F7.setEditable(false);
        F7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F7.setHighlighter(null);
        jScrollPane105.setViewportView(F7);

        F9.setEditable(false);
        F9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        F9.setHighlighter(null);
        jScrollPane106.setViewportView(F9);

        D1.setEditable(false);
        D1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D1.setHighlighter(null);
        jScrollPane107.setViewportView(D1);

        D2.setEditable(false);
        D2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D2.setHighlighter(null);
        jScrollPane108.setViewportView(D2);

        D7.setEditable(false);
        D7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D7.setHighlighter(null);
        jScrollPane125.setViewportView(D7);

        D4.setEditable(false);
        D4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D4.setHighlighter(null);
        jScrollPane109.setViewportView(D4);

        D8.setEditable(false);
        D8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D8.setHighlighter(null);
        jScrollPane126.setViewportView(D8);

        D3.setEditable(false);
        D3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D3.setHighlighter(null);
        jScrollPane110.setViewportView(D3);

        E1.setEditable(false);
        E1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E1.setHighlighter(null);
        jScrollPane111.setViewportView(E1);

        E2.setEditable(false);
        E2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E2.setHighlighter(null);
        jScrollPane112.setViewportView(E2);

        E3.setEditable(false);
        E3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        E3.setHighlighter(null);
        jScrollPane113.setViewportView(E3);

        D5.setEditable(false);
        D5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        D5.setHighlighter(null);
        jScrollPane114.setViewportView(D5);

        I3.setEditable(false);
        I3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I3.setHighlighter(null);
        jScrollPane135.setViewportView(I3);

        G9.setEditable(false);
        G9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G9.setHighlighter(null);
        jScrollPane136.setViewportView(G9);

        I5.setEditable(false);
        I5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I5.setHighlighter(null);
        jScrollPane137.setViewportView(I5);

        H7.setEditable(false);
        H7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H7.setHighlighter(null);
        jScrollPane138.setViewportView(H7);

        H8.setEditable(false);
        H8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H8.setHighlighter(null);
        jScrollPane139.setViewportView(H8);

        I4.setEditable(false);
        I4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I4.setHighlighter(null);
        jScrollPane140.setViewportView(I4);

        I6.setEditable(false);
        I6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I6.setHighlighter(null);
        jScrollPane141.setViewportView(I6);

        H9.setEditable(false);
        H9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H9.setHighlighter(null);
        jScrollPane142.setViewportView(H9);

        G7.setEditable(false);
        G7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G7.setHighlighter(null);
        jScrollPane143.setViewportView(G7);

        I8.setEditable(false);
        I8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I8.setHighlighter(null);
        jScrollPane144.setViewportView(I8);

        G8.setEditable(false);
        G8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G8.setHighlighter(null);
        jScrollPane145.setViewportView(G8);

        I7.setEditable(false);
        I7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I7.setHighlighter(null);
        jScrollPane146.setViewportView(I7);

        I9.setEditable(false);
        I9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I9.setHighlighter(null);
        jScrollPane147.setViewportView(I9);

        G1.setEditable(false);
        G1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G1.setHighlighter(null);
        jScrollPane148.setViewportView(G1);

        H3.setEditable(false);
        H3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H3.setHighlighter(null);
        jScrollPane127.setViewportView(H3);

        G2.setEditable(false);
        G2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G2.setHighlighter(null);
        jScrollPane149.setViewportView(G2);

        G5.setEditable(false);
        G5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G5.setHighlighter(null);
        jScrollPane128.setViewportView(G5);

        I2.setEditable(false);
        I2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I2.setHighlighter(null);
        jScrollPane129.setViewportView(I2);

        G4.setEditable(false);
        G4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G4.setHighlighter(null);
        jScrollPane150.setViewportView(G4);

        G3.setEditable(false);
        G3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G3.setHighlighter(null);
        jScrollPane151.setViewportView(G3);

        G6.setEditable(false);
        G6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        G6.setHighlighter(null);
        jScrollPane130.setViewportView(G6);

        H1.setEditable(false);
        H1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H1.setHighlighter(null);
        jScrollPane152.setViewportView(H1);

        H4.setEditable(false);
        H4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H4.setHighlighter(null);
        jScrollPane131.setViewportView(H4);

        H2.setEditable(false);
        H2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H2.setHighlighter(null);
        jScrollPane153.setViewportView(H2);

        H5.setEditable(false);
        H5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H5.setHighlighter(null);
        jScrollPane132.setViewportView(H5);

        H6.setEditable(false);
        H6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        H6.setHighlighter(null);
        jScrollPane133.setViewportView(H6);

        I1.setEditable(false);
        I1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        I1.setHighlighter(null);
        jScrollPane134.setViewportView(I1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane152, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane153, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane127, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane134, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane129, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane135, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane148, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane149, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane151, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane107, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane108, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane110, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane111, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane112, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane113, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane120, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane115, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane121, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane82, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane83, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane84, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane85, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane86, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane87, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane89, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane88, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane90, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane109, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane114, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane116, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane117, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane118, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane119, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane123, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane122, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane124, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane150, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane128, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane130, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane131, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane132, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane133, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane140, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane137, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane141, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane143, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane145, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane136, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane138, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane139, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane142, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane146, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane144, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane147, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane125, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane126, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane100, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane101, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane102, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane103, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane105, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane104, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane106, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane91, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane92, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane93, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane94, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane95, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane96, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane98, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane97, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane99, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane91, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane92, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane93, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane94, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane95, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane96, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane98, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane97, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane99, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane82, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane83, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane84, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane85, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane86, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane87, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane89, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane88, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane90, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane125, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane126, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane100, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane101, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane102, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane103, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane105, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane104, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane106, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane109, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane114, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane116, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane117, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane118, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane119, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane123, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane122, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane124, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane107, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane108, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane110, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane111, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane112, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane113, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane120, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane115, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane121, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane143, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane145, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane136, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane138, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane139, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane142, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane146, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane144, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane147, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane150, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane128, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane130, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane131, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane132, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane133, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane140, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane137, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane141, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane148, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane149, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane151, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane152, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane153, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane127, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane134, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane129, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane135, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        legalOne.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalOne.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalOne.setEnabled(false);
        legalOne.setFocusable(false);
        legalOne.setHighlighter(null);
        jScrollPane10.setViewportView(legalOne);

        legalTwo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalTwo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalTwo.setEnabled(false);
        legalTwo.setFocusable(false);
        legalTwo.setHighlighter(null);
        jScrollPane11.setViewportView(legalTwo);

        legalThree.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalThree.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalThree.setEnabled(false);
        legalThree.setFocusable(false);
        legalThree.setHighlighter(null);
        jScrollPane12.setViewportView(legalThree);

        legalFour.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalFour.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalFour.setEnabled(false);
        legalFour.setFocusable(false);
        legalFour.setHighlighter(null);
        jScrollPane13.setViewportView(legalFour);

        legalFive.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalFive.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalFive.setEnabled(false);
        legalFive.setFocusable(false);
        legalFive.setHighlighter(null);
        jScrollPane14.setViewportView(legalFive);

        legalSix.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalSix.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalSix.setEnabled(false);
        legalSix.setFocusable(false);
        legalSix.setHighlighter(null);
        jScrollPane15.setViewportView(legalSix);

        legalEight.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalEight.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalEight.setEnabled(false);
        legalEight.setFocusable(false);
        legalEight.setHighlighter(null);
        jScrollPane16.setViewportView(legalEight);

        legalSeven.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalSeven.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalSeven.setEnabled(false);
        legalSeven.setFocusable(false);
        legalSeven.setHighlighter(null);
        jScrollPane17.setViewportView(legalSeven);

        legalNine.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        legalNine.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        legalNine.setEnabled(false);
        legalNine.setFocusable(false);
        legalNine.setHighlighter(null);
        jScrollPane18.setViewportView(legalNine);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Legal Moves");

        statusField.setEditable(false);
        statusField.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        statusField.setFocusable(false);
        statusField.setHighlighter(null);
        jScrollPane19.setViewportView(statusField);

        saveButton.setBackground(new java.awt.Color(153, 255, 153));
        saveButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        saveButton.setText("Save Puzzle");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        renameButton.setBackground(new java.awt.Color(255, 102, 255));
        renameButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        renameButton.setText("Rename Puzzle");
        renameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameButtonActionPerformed(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(255, 102, 102));
        clearButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clearButton.setText("Clear Puzzle");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        quitButton.setBackground(new java.awt.Color(153, 153, 153));
        quitButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addComponent(saveButton)
                            .addComponent(renameButton)
                            .addComponent(clearButton)
                            .addComponent(quitButton)))
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(renameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearButton)
                        .addGap(31, 31, 31)
                        .addComponent(quitButton))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Button Action Event Handlers">
    /* Saves the puzzle */
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        this.save();
    }//GEN-LAST:event_saveButtonActionPerformed
    /* Renames the puzzle file */
    private void renameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameButtonActionPerformed
        this.saveAs();
    }//GEN-LAST:event_renameButtonActionPerformed
    /* Clears out the puzzle */
    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.clear();
    }//GEN-LAST:event_clearButtonActionPerformed
    /* Returns back to the custom puzzles menu */
    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        this.quit();
    }//GEN-LAST:event_quitButtonActionPerformed
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Component Declarations">
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
    private javax.swing.JTextPane I1;
    private javax.swing.JTextPane I2;
    private javax.swing.JTextPane I3;
    private javax.swing.JTextPane I4;
    private javax.swing.JTextPane I5;
    private javax.swing.JTextPane I6;
    private javax.swing.JTextPane I7;
    private javax.swing.JTextPane I8;
    private javax.swing.JTextPane I9;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane100;
    private javax.swing.JScrollPane jScrollPane101;
    private javax.swing.JScrollPane jScrollPane102;
    private javax.swing.JScrollPane jScrollPane103;
    private javax.swing.JScrollPane jScrollPane104;
    private javax.swing.JScrollPane jScrollPane105;
    private javax.swing.JScrollPane jScrollPane106;
    private javax.swing.JScrollPane jScrollPane107;
    private javax.swing.JScrollPane jScrollPane108;
    private javax.swing.JScrollPane jScrollPane109;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane110;
    private javax.swing.JScrollPane jScrollPane111;
    private javax.swing.JScrollPane jScrollPane112;
    private javax.swing.JScrollPane jScrollPane113;
    private javax.swing.JScrollPane jScrollPane114;
    private javax.swing.JScrollPane jScrollPane115;
    private javax.swing.JScrollPane jScrollPane116;
    private javax.swing.JScrollPane jScrollPane117;
    private javax.swing.JScrollPane jScrollPane118;
    private javax.swing.JScrollPane jScrollPane119;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane120;
    private javax.swing.JScrollPane jScrollPane121;
    private javax.swing.JScrollPane jScrollPane122;
    private javax.swing.JScrollPane jScrollPane123;
    private javax.swing.JScrollPane jScrollPane124;
    private javax.swing.JScrollPane jScrollPane125;
    private javax.swing.JScrollPane jScrollPane126;
    private javax.swing.JScrollPane jScrollPane127;
    private javax.swing.JScrollPane jScrollPane128;
    private javax.swing.JScrollPane jScrollPane129;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane130;
    private javax.swing.JScrollPane jScrollPane131;
    private javax.swing.JScrollPane jScrollPane132;
    private javax.swing.JScrollPane jScrollPane133;
    private javax.swing.JScrollPane jScrollPane134;
    private javax.swing.JScrollPane jScrollPane135;
    private javax.swing.JScrollPane jScrollPane136;
    private javax.swing.JScrollPane jScrollPane137;
    private javax.swing.JScrollPane jScrollPane138;
    private javax.swing.JScrollPane jScrollPane139;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane140;
    private javax.swing.JScrollPane jScrollPane141;
    private javax.swing.JScrollPane jScrollPane142;
    private javax.swing.JScrollPane jScrollPane143;
    private javax.swing.JScrollPane jScrollPane144;
    private javax.swing.JScrollPane jScrollPane145;
    private javax.swing.JScrollPane jScrollPane146;
    private javax.swing.JScrollPane jScrollPane147;
    private javax.swing.JScrollPane jScrollPane148;
    private javax.swing.JScrollPane jScrollPane149;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane150;
    private javax.swing.JScrollPane jScrollPane151;
    private javax.swing.JScrollPane jScrollPane152;
    private javax.swing.JScrollPane jScrollPane153;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane82;
    private javax.swing.JScrollPane jScrollPane83;
    private javax.swing.JScrollPane jScrollPane84;
    private javax.swing.JScrollPane jScrollPane85;
    private javax.swing.JScrollPane jScrollPane86;
    private javax.swing.JScrollPane jScrollPane87;
    private javax.swing.JScrollPane jScrollPane88;
    private javax.swing.JScrollPane jScrollPane89;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPane90;
    private javax.swing.JScrollPane jScrollPane91;
    private javax.swing.JScrollPane jScrollPane92;
    private javax.swing.JScrollPane jScrollPane93;
    private javax.swing.JScrollPane jScrollPane94;
    private javax.swing.JScrollPane jScrollPane95;
    private javax.swing.JScrollPane jScrollPane96;
    private javax.swing.JScrollPane jScrollPane97;
    private javax.swing.JScrollPane jScrollPane98;
    private javax.swing.JScrollPane jScrollPane99;
    private javax.swing.JTextPane legalEight;
    private javax.swing.JTextPane legalFive;
    private javax.swing.JTextPane legalFour;
    private javax.swing.JTextPane legalNine;
    private javax.swing.JTextPane legalOne;
    private javax.swing.JTextPane legalSeven;
    private javax.swing.JTextPane legalSix;
    private javax.swing.JTextPane legalThree;
    private javax.swing.JTextPane legalTwo;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton renameButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextPane statusField;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

} // End PuzzleEditorFrame class