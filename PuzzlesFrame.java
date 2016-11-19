/**
 * PuzzlesFrameFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

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

        this.puzzleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
        });

        this.setVisible(true);
    }


    /***/
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
        this.puzzleCount.setText("Finished loading files, " + list.length + " puzzle(s) found.");
    }


    /***/
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
        } else { return; }

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
        ////////BookFrame addressBook = new BookFrame(name);
    }


    /***/
    private void editPuzzle() {
        if (this.puzzleList.getSelectedValue() == null) {
            WindowUtility.displayInfo("You must select a puzzle to edit.", "Note!");
        } else {
            WindowUtility.displayInfo("FIXME!!!!", "FIXME!!!");
        }
    }


    /***/
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
                this.bindIntoTable();
            }
        }
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
        puzzleCount = new javax.swing.JTextField();
        playButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        puzzleList.setBorder(new javax.swing.border.MatteBorder(null));
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

        puzzleCount.setEditable(false);
        puzzleCount.setBackground(new java.awt.Color(255, 255, 204));
        puzzleCount.setEnabled(false);
        puzzleCount.setFocusable(false);
        puzzleCount.setHighlighter(null);

        playButton.setBackground(new java.awt.Color(255, 102, 255));
        playButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(newButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(playButton))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                            .addComponent(puzzleCount)
                            .addComponent(backButton)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel1)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(puzzleCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* Button Actions */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        MainFrame m = new MainFrame(this.getX(), this.getY());
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed
    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        this.newPuzzle();
    }//GEN-LAST:event_newButtonActionPerformed
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        this.editPuzzle();
    }//GEN-LAST:event_editButtonActionPerformed
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        this.deletePuzzle();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playButtonActionPerformed

    // <editor-fold defaultstate="collapsed" desc="Component Declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newButton;
    private javax.swing.JButton playButton;
    private javax.swing.JTextField puzzleCount;
    private javax.swing.JList<String> puzzleList;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

}