/**
 * PuzzlesFrameFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that contains a GUI for the custom puzzles menu. Users can create new
 * puzzle files, open and edit existing files, delete, and play saved puzzles.
 */
package sudoku;


/* Imports */
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIDefaults;

public class PuzzlesFrame extends JFrame {

    /* Default constructor */
    public PuzzlesFrame(int x, int y) {

        /* Initialize components */
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sudoku_icon.png")));
        this.setTitle("Sudoku");
        this.setLocation(x, y);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* Creates the subfolder where the user's puzzles are saved. */
        File dir = new File(FileUtility.MY_PUZZLES_PATH);
        if (!dir.exists())
            dir.mkdir();
        this.bindIntoTable();

        /* Sets the status text's background color */
        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane[Enabled].backgroundPainter", GUIColors.MENU_BACKGROUND);
        this.puzzleDate.putClientProperty("Nimbus.Overrides", defaults);
        this.puzzleDate.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        this.puzzleDate.setBackground(GUIColors.MENU_BACKGROUND);

        /* Sets the button icons */
        try {
            Image searchImg = ImageIO.read(getClass().getResource("search.png"));
            Image clearImg = ImageIO.read(getClass().getResource("clear.png"));
            searchButton.setIcon(new ImageIcon(searchImg));
            clearButton.setIcon(new ImageIcon(clearImg));
        } catch (IOException ex) {/* Ignore exceptions */}

        /* Code to update the text displaying the last modified date of a selected puzzle */
        this.puzzleDate.setText("Last Modified: ");
        this.puzzleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String f = puzzleList.getSelectedValue();
                if (f != null) {
                    File file = FileUtility.getFile(f + ".txt", FileUtility.MY_PUZZLES_PATH);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy  h:mm a");
                    puzzleDate.setText("Last Modified: " + sdf.format(file.lastModified()));
                } else {
                    puzzleDate.setText("Last Modified: ");
                }
            }
        });

        /* Asks user if they're sure when closing the window. */
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                if (WindowUtility.askYesNo("Are you sure you want to quit?",
                        "Quitting"))
                    System.exit(0);
            }
        });

        this.setVisible(true);
    }


    /**
     * Updates the table in the frame. Displays all the puzzles inside the 'My Puzzles'
     * directory into the list.
     */
    private void bindIntoTable() {

        /* Obtain all saved puzzles from the folder */
        this.puzzleList.clearSelection();
        ArrayList<String> txtFiles = new ArrayList<String>();
        File folder = new File(FileUtility.MY_PUZZLES_PATH);
        File[] fileList = folder.listFiles();

        /* Add each puzzle found into table */
        for (File file : fileList) {
            if (file.isFile() && file.toString().endsWith(".txt"))
                txtFiles.add(file.getName().substring(0, file.getName().length()-4));
        }

        /* Display all puzzles into menu */
        String[] list = txtFiles.toArray(new String[txtFiles.size()]);
        this.puzzleList.setListData(list);
    }


    /**
     * Invoked when the user wants to create a new puzzle. Asks for the name of
     * the puzzle, creates the file, then opens the new puzzle in the editing
     * window.
     */
    private void newPuzzle() {

        /* Gets a new file name from the user, validates the name */
        String name = WindowUtility.getEntry("Enter the name of your new puzzle.");
        if (name != null) {
            /* Verify that the file name is valid */
            if (!(FileUtility.fileNameValid(name))) {
                WindowUtility.errorMessage("The name you entered is an illegal name.",
                        "Error!");
                return;
            }
            /* Verify that the file name is unique */
            else if (!(FileUtility.nameIsUnique(name + ".txt", FileUtility.MY_PUZZLES_PATH))) {
                WindowUtility.errorMessage("There already exists another puzzle with this name."
                        + "\nChoose a different name.",
                        "Error!");
                return;
            }
        } else {return;}

        /* Create the new file, opens a new puzzle */
        name += ".txt";
        File dir = new File(FileUtility.MY_PUZZLES_PATH, name);
        try {
            dir.createNewFile();
        } catch (IOException ex) {
            WindowUtility.errorMessage("Failed to create the puzzle.",
                    "Error!");
            return;
        }
        this.bindIntoTable();
        SudokuPuzzle p = new SudokuPuzzle();
        FileUtility.saveGame(p, 0, FileUtility.MY_PUZZLES_PATH + name);
        PuzzleEditorFrame f = new PuzzleEditorFrame(p, name.substring(0, name.length()-4),
                this.getX(), this.getY());
        this.dispose();
    }


    /**
     * Opens the selected puzzle in the editing frame.
     */
    private void editPuzzle() {
        if (this.puzzleList.getSelectedValue() == null) {
            WindowUtility.displayInfo("You must select a puzzle to edit.", "Note!");
        } else {
            try {
                SudokuPuzzle p = FileUtility.loadGame(FileUtility.MY_PUZZLES_PATH + this.puzzleList.getSelectedValue() + ".txt");
                PuzzleEditorFrame f = new PuzzleEditorFrame(p, this.puzzleList.getSelectedValue(), this.getX(), this.getY());
                this.dispose();
            } catch (Exception ex) {/* Ignore exceptions */}
        }
    }


    /**
     * Deletes the selected puzzle. Asks the user if they're sure, and deletes the
     * file from the 'My Puzzles' directory if confirmed.
     */
    private void deletePuzzle() {
        if (this.puzzleList.getSelectedValue() == null) {
            WindowUtility.displayInfo("You must select a puzzle to delete.", "Note!");
        } else {
            if (WindowUtility.askYesNo("Are you sure you want to delete the puzzle:\n" +
                    this.puzzleList.getSelectedValue() + "?", "Warning!")) {
                File file = new File(FileUtility.MY_PUZZLES_PATH,
                        this.puzzleList.getSelectedValue() + ".txt");
                if (!file.delete())
                    WindowUtility.errorMessage("Failed to delete the puzzle.",
                            "Error!");
                this.search();
            }
        }
    }


    /***/
    private void play() {
        String name = this.puzzleList.getSelectedValue();
        if (name == null) {
            WindowUtility.displayInfo("You must select a puzzle to play.", "Note!");
            return;
        }
        SudokuPuzzle p = FileUtility.loadGame(FileUtility.MY_PUZZLES_PATH + name + ".txt");
        SudokuSolver s = new SudokuSolver(p);
        if (!s.isSolvable()) {
            WindowUtility.displayInfo("This puzzle is currently unsolvable.", "Note!");
            return;
        }
        SudokuFrame f = new SudokuFrame(p, false,
                FileUtility.MY_PUZZLES_PATH + name + ".txt", this.getX(), this.getY());
        this.dispose();
    }


    /**
     * FIXME
     */
    private void importPuzzle() {
        FileDialog fd = new FileDialog(this, "Import", FileDialog.LOAD);
        fd.setVisible(true);
        String filePath = fd.getDirectory();
        String fileName = fd.getFile();
        if (filePath == null || fileName == null)
            return;
        if (!fileName.endsWith(".txt")) {
            WindowUtility.errorMessage("Invalid file type.", "Error!");
            return;
        }
        if (!FileUtility.nameIsUnique(fileName, FileUtility.MY_PUZZLES_PATH)) {
            WindowUtility.errorMessage("Failed to import the puzzle."
                    + "\nAnother puzzle already exists with that name.",
                        "Error!");
            return;
        }
        if (!FileUtility.copyFile(filePath + fileName, FileUtility.MY_PUZZLES_PATH + fileName))
            WindowUtility.errorMessage("Failed to import the puzzle."
                    + "\nCould not copy the contents of the file.",
                        "Error!");
        this.bindIntoTable();
    }


    /**
     * FIXME
     */
    private void exportPuzzle() {
        String old = this.puzzleList.getSelectedValue();
        if (old == null) {
            WindowUtility.displayInfo("You must select a puzzle to export.", "Note!");
            return;
        }
        FileDialog fd = new FileDialog(this, "Export", FileDialog.SAVE);
        fd.setFile(old);
        fd.setVisible(true);
        String filePath = fd.getDirectory();
        String fileName = fd.getFile();
        if (filePath == null || fileName == null)
            return;
        if (!(FileUtility.fileNameValid(fileName))) {
            WindowUtility.errorMessage("The name you entered is an illegal name.",
                        "Error!");
            return;
        }
        if (!(FileUtility.nameIsUnique(fileName + ".txt", filePath))) {
            WindowUtility.errorMessage("There already exists file with this name."
                        + "\nChoose a different name.",
                        "Error!");
            return;
        }
        if (!fileName.endsWith(".txt"))
            fileName += ".txt";
        if (!FileUtility.copyFile(FileUtility.MY_PUZZLES_PATH + old + ".txt", filePath + fileName))
            WindowUtility.errorMessage("Failed to export the puzzle.",
                        "Error!");
    }


    /**
     * FIXME
     */
    private void search() {
        this.puzzleList.clearSelection();
        ArrayList<String> txtFiles = new ArrayList<String>();
        File folder = new File(FileUtility.MY_PUZZLES_PATH);
        File[] fileList = folder.listFiles();

        for (File file : fileList) {
            String s = file.getName().toLowerCase().substring(0, file.getName().length()-4);
            if (file.isFile() && file.toString().endsWith(".txt") &&
                    s.toLowerCase().contains(this.searchField.getText().toLowerCase()))
                txtFiles.add(file.getName().substring(0, file.getName().length()-4));
        }

        String[] list = txtFiles.toArray(new String[txtFiles.size()]);
        this.puzzleList.setListData(list);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        puzzleList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        puzzleDate = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newOption = new javax.swing.JMenuItem();
        editOption = new javax.swing.JMenuItem();
        deleteOption = new javax.swing.JMenuItem();
        playOption = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        importOption = new javax.swing.JMenuItem();
        exportOption = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        backOption = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        puzzleList.setBorder(new javax.swing.border.MatteBorder(null));
        puzzleList.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jScrollPane1.setViewportView(puzzleList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("My Puzzles");

        backButton.setBackground(new java.awt.Color(153, 153, 153));
        backButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        newButton.setBackground(new java.awt.Color(153, 255, 153));
        newButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        editButton.setBackground(new java.awt.Color(153, 153, 255));
        editButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(255, 102, 102));
        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        playButton.setBackground(new java.awt.Color(255, 102, 255));
        playButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        puzzleDate.setEditable(false);
        puzzleDate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        puzzleDate.setFocusable(false);
        puzzleDate.setHighlighter(null);
        jScrollPane3.setViewportView(puzzleDate);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Search");

        searchField.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        searchButton.setBackground(new java.awt.Color(185, 185, 255));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(185, 185, 255));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(playButton))
                    .addComponent(jScrollPane1)
                    .addComponent(backButton)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jMenu1.setText("File");

        newOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newOption.setText("New");
        newOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOptionActionPerformed(evt);
            }
        });
        jMenu1.add(newOption);

        editOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        editOption.setText("Edit");
        editOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOptionActionPerformed(evt);
            }
        });
        jMenu1.add(editOption);

        deleteOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        deleteOption.setText("Delete");
        deleteOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteOptionActionPerformed(evt);
            }
        });
        jMenu1.add(deleteOption);

        playOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        playOption.setText("Play");
        playOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playOptionActionPerformed(evt);
            }
        });
        jMenu1.add(playOption);
        jMenu1.add(jSeparator1);

        importOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        importOption.setText("Import");
        importOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importOptionActionPerformed(evt);
            }
        });
        jMenu1.add(importOption);

        exportOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exportOption.setText("Export");
        exportOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportOptionActionPerformed(evt);
            }
        });
        jMenu1.add(exportOption);
        jMenu1.add(jSeparator2);

        backOption.setText("Go Back");
        backOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backOptionActionPerformed(evt);
            }
        });
        jMenu1.add(backOption);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Button & Menu Action Event Handlers">
    /* Returns to the main menu */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        MainFrame f = new MainFrame(this.getX(), this.getY());
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed
    /* Creates a new custom puzzle */
    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        this.newPuzzle();
    }//GEN-LAST:event_newButtonActionPerformed
    /* Opens the selected puzzle file for editing */
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        this.editPuzzle();
    }//GEN-LAST:event_editButtonActionPerformed
    /* Deletes the selected file */
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        this.deletePuzzle();
    }//GEN-LAST:event_deleteButtonActionPerformed
    /* Opens the selected file to play */
    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        this.play();
    }//GEN-LAST:event_playButtonActionPerformed
    /* Creates a new custom puzzle */
    private void newOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newOptionActionPerformed
        this.newPuzzle();
    }//GEN-LAST:event_newOptionActionPerformed
    /* Opens the selected puzzle file for editing */
    private void editOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editOptionActionPerformed
        this.editPuzzle();
    }//GEN-LAST:event_editOptionActionPerformed
    /* Deletes the selected file */
    private void deleteOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteOptionActionPerformed
        this.deletePuzzle();
    }//GEN-LAST:event_deleteOptionActionPerformed
    /* Opens the selected file to play */
    private void playOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playOptionActionPerformed
        this.play();
    }//GEN-LAST:event_playOptionActionPerformed
    /* Imports a puzzle file */
    private void importOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importOptionActionPerformed
        this.importPuzzle();
    }//GEN-LAST:event_importOptionActionPerformed
    /* Exports the selected puzzle file */
    private void exportOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportOptionActionPerformed
        this.exportPuzzle();
    }//GEN-LAST:event_exportOptionActionPerformed
    /* Returns to the main menu */
    private void backOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backOptionActionPerformed
        MainFrame f = new MainFrame(this.getX(), this.getY());
        this.dispose();
    }//GEN-LAST:event_backOptionActionPerformed
    /* Searches for files */
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        this.search();
    }//GEN-LAST:event_searchButtonActionPerformed
    /* Undo a search */
    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.searchField.setText("");
        this.search();
    }//GEN-LAST:event_clearButtonActionPerformed
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Component Declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JMenuItem backOption;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JMenuItem deleteOption;
    private javax.swing.JButton editButton;
    private javax.swing.JMenuItem editOption;
    private javax.swing.JMenuItem exportOption;
    private javax.swing.JMenuItem importOption;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JButton newButton;
    private javax.swing.JMenuItem newOption;
    private javax.swing.JButton playButton;
    private javax.swing.JMenuItem playOption;
    private javax.swing.JTextPane puzzleDate;
    private javax.swing.JList<String> puzzleList;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

} // End PuzzlesFrame class