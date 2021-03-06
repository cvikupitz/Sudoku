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
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.UIDefaults;

public class PuzzlesFrame extends JFrame {

    /* Default constructor */
    public PuzzlesFrame(int x, int y) {


        /* Initialize components */
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(
                "icons/sudoku_icon.png")));
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

        /* Code to update the text displaying the last modified date of a selected puzzle */
        this.puzzleDate.setText("Last Modified: ");
        this.puzzleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String f = puzzleList.getSelectedValue();
                if (f != null) {
                    File file = FileUtility.getFile(f + ".dat", FileUtility.MY_PUZZLES_PATH);
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
                        "Quitting")) {
                    FileUtility.saveBestTimes();
                    System.exit(0);
                }
            }
        });

        /* Sets the UI visible to the user */
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
            if (file.isFile() && file.toString().endsWith(".dat"))
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
                WindowUtility.errorMessage("The name you entered is an illegal name.\n"
                        + "Choose a different name.",
                        "Error!");
                return;
            }
            /* Verify that the file name is unique */
            else if (!(FileUtility.nameIsUnique(name + ".dat", FileUtility.MY_PUZZLES_PATH))) {
                WindowUtility.errorMessage("There already exists another puzzle with this name."
                        + "\nChoose a different name.",
                        "Error!");
                return;
            }
        } else {return;}

        /* Create the new file, opens a new puzzle */
        name += ".dat";
        File dir = new File(FileUtility.MY_PUZZLES_PATH, name);
        try {
            dir.createNewFile();
        } catch (IOException ex) {
            WindowUtility.errorMessage("Failed to create the puzzle.",
                    "Error!");
            return;
        }

        /* Creates a new puzzle, opens the new puzzle inside the puzzle editor */
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

        /* User does not select a puzzle form the list */
        if (this.puzzleList.getSelectedValue() == null) {
            WindowUtility.displayInfo("You must select a puzzle to edit.", "Note!");
        } else {
            try {

                /* Loads the puzzle from the file, opens it in the editor */
                SudokuPuzzle p = FileUtility.loadGame(FileUtility.MY_PUZZLES_PATH + this.puzzleList.getSelectedValue() + ".dat");
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

        /* User does not select a puzzle from the list */
        if (this.puzzleList.getSelectedValue() == null) {
            WindowUtility.displayInfo("You must select a puzzle to delete.", "Note!");
        } else {

            /* Asks the user if they're sure */
            if (WindowUtility.askYesNo("Are you sure you want to delete the puzzle:\n" +
                    this.puzzleList.getSelectedValue() + "?", "Warning!")) {

                /* Obtains and deletes the specified file */
                File file = new File(FileUtility.MY_PUZZLES_PATH +
                        this.puzzleList.getSelectedValue() + ".dat");
                if (!file.delete())
                    WindowUtility.errorMessage("Failed to delete the puzzle.",
                            "Error!");

                /* Updates the search if user searched for the file to delete */
                this.search();
            }
        }
    }


    /**
     * Opens the selected puzzle file to play. This is invoked when the user clicks
     * the play button. Only works if the puzzle isn't completely filled in, is
     * unsolvable, or is already solved. A message displays if puzzle cannot be
     * played, otherwise opens in a SudokuFrame.
     */
    private void play() {

        /* User does not select a puzzle from the list */
        String name = this.puzzleList.getSelectedValue();
        if (name == null) {
            WindowUtility.displayInfo("You must select a puzzle to play.", "Note!");
            return;
        }

        /* Loads the puzzle, checks to see if puzzle is already solved */
        SudokuPuzzle p = FileUtility.loadGame(FileUtility.MY_PUZZLES_PATH + name + ".dat");
        if (p.isComplete()) {
            WindowUtility.displayInfo("This puzzle is already solved.", "Note!");
            return;
        }

        /* Checks to see if puzzle is in an unsolvable state */
        SudokuSolver s = new SudokuSolver(p);
        if (!s.isSolvable() || p.getNumberFilled() == 81) {
            WindowUtility.displayInfo("This puzzle is currently unsolvable.", "Note!");
            return;
        }

        /* All constraints passed, puzzle loads into SudokuFrame successfully */
        SudokuFrame f = new SudokuFrame(p, false,
                FileUtility.MY_PUZZLES_PATH + name + ".dat", this.getX(), this.getY());
        this.dispose();
    }


    /**
     * Imports a puzzle file from the user's computer. Constraints include importing
     * a file without the .dat extension, and name must be unique. Imported if
     * satisfied, or displays error message if not.
     */
    private void importPuzzle() {

        /* Open importing window, user finds and selects the file */
        FileDialog fd = new FileDialog(this, "Import", FileDialog.LOAD);
        fd.setVisible(true);
        String filePath = fd.getDirectory();
        String fileName = fd.getFile();
        if (filePath == null || fileName == null)
            return;

        /* Check to see if the file type is legal */
        if (!fileName.endsWith(".dat")) {
            WindowUtility.errorMessage("The file selected is illegal.\n"
                    + "You may only import files with a .dat extension.", "Error!");
            return;
        }

        /* Check to see if the file name is unique */
        if (!FileUtility.nameIsUnique(fileName, FileUtility.MY_PUZZLES_PATH)) {
            WindowUtility.errorMessage("Failed to import the puzzle."
                    + "\nAnother puzzle already exists with that name.",
                        "Error!");
            return;
        }

        /* Copies the seleted file into the puzzles directory, updates the lists */
        if (!FileUtility.copyFile(filePath + fileName, FileUtility.MY_PUZZLES_PATH + fileName))
            WindowUtility.errorMessage("Failed to import the puzzle."
                    + "\nCould not copy the contents of the file.",
                        "Error!");
        this.bindIntoTable();
    }


    /**
     * Exports the selected puzzle file to the user's computer. Constraints include
     * renaming to a legal name and the name is unique. Exports successfully if
     * satisfied, or an error message is displayed if not.
     */
    private void exportPuzzle() {

        /* User did not select a file from the list */
        String old = this.puzzleList.getSelectedValue();
        if (old == null) {
            WindowUtility.displayInfo("You must select a puzzle to export.", "Note!");
            return;
        }

        /* Opens the exporting window, user navigates to directory to export to */
        FileDialog fd = new FileDialog(this, "Export", FileDialog.SAVE);
        fd.setFile(old);
        fd.setVisible(true);
        String filePath = fd.getDirectory();
        String fileName = fd.getFile();
        if (filePath == null || fileName == null)
            return;

        /* Check to see if the file name is legal */
        if (!(FileUtility.fileNameValid(fileName))) {
            WindowUtility.errorMessage("The name you entered is an illegal name.",
                        "Error!");
            return;
        }

        /* Check to see if the file name is unique */
        if (!(FileUtility.nameIsUnique(fileName + ".dat", filePath))) {
            WindowUtility.errorMessage("There already exists file with this name."
                        + "\nChoose a different name.",
                        "Error!");
            return;
        }

        /* Exports the file to the desired directory */
        if (!fileName.endsWith(".dat"))
            fileName += ".dat";
        if (!FileUtility.copyFile(FileUtility.MY_PUZZLES_PATH + old + ".dat", filePath + fileName))
            WindowUtility.errorMessage("Failed to export the puzzle.",
                        "Error!");
    }


    /**
     * Searches for all the puzzle files continaing or matching the string inside
     * the search text box. Matches considered if the file name contains the string
     * and is not case-sensitive. Updates the list to only show matches.
     */
    private void search() {

        /* Get the list of all the puzzle files to scan */
        this.puzzleList.clearSelection();
        ArrayList<String> txtFiles = new ArrayList<String>();
        File folder = new File(FileUtility.MY_PUZZLES_PATH);
        File[] fileList = folder.listFiles();

        /* Iterates through all the files */
        for (File file : fileList) {

            /* Examines each name, scans to see if file name contains the phrase */
            String s = file.getName().toLowerCase().substring(0, file.getName().length()-4);
            if (file.isFile() && file.toString().endsWith(".dat") &&
                    s.toLowerCase().contains(this.searchField.getText().toLowerCase()))

                /* Matches added to the list */
                txtFiles.add(file.getName().substring(0, file.getName().length()-4));
        }

        /* List updated to only include the matches */
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

        searchButton.setBackground(new java.awt.Color(204, 204, 255));
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/search_icon.png"))); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/clear_icon.png"))); // NOI18N
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jScrollPane3)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(searchButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(clearButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jMenu1.setText("File");

        newOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/new_icon.png"))); // NOI18N
        newOption.setText("   New");
        newOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOptionActionPerformed(evt);
            }
        });
        jMenu1.add(newOption);

        editOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        editOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/open_icon.png"))); // NOI18N
        editOption.setText("   Edit");
        editOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOptionActionPerformed(evt);
            }
        });
        jMenu1.add(editOption);

        deleteOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        deleteOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/delete_icon.png"))); // NOI18N
        deleteOption.setText("   Delete");
        deleteOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteOptionActionPerformed(evt);
            }
        });
        jMenu1.add(deleteOption);

        playOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        playOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/play_icon.png"))); // NOI18N
        playOption.setText("   Play");
        playOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playOptionActionPerformed(evt);
            }
        });
        jMenu1.add(playOption);
        jMenu1.add(jSeparator1);

        importOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        importOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/import_icon.png"))); // NOI18N
        importOption.setText("   Import");
        importOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importOptionActionPerformed(evt);
            }
        });
        jMenu1.add(importOption);

        exportOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exportOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/export_icon.png"))); // NOI18N
        exportOption.setText("   Export");
        exportOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportOptionActionPerformed(evt);
            }
        });
        jMenu1.add(exportOption);
        jMenu1.add(jSeparator2);

        backOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/icons/close_icon.png"))); // NOI18N
        backOption.setText("   Go Back");
        backOption.setToolTipText("");
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
    /* Searches for a puzzle */
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        this.search();
    }//GEN-LAST:event_searchButtonActionPerformed
    /* Undoes a search */
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