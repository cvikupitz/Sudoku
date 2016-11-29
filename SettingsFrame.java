/**
 * SettingsFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that contains a GUI for the settings menu. Contains a back button that
 * goes back to the MainFrame GUI, and radio buttons for enabling/disabling program
 * settings and features.
 */
package sudoku;


/* Imports */
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;

public class SettingsFrame extends JFrame {

    /* Declare private members */
    private final ButtonGroup timerGroup, legalMovesGroup, highlighterGroup,
            conflictGroup, hintsGroup, solutionsgroup;

    /* Default constructors */
    public SettingsFrame(int x, int y) {

        /* Initialize components */
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sudoku_icon.png")));
        this.setTitle("Sudoku");
        this.setLocation(x, y);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* Create the button groups, add the radio buttons */
        this.timerGroup = new ButtonGroup();
        this.timerGroup.add(this.timerOn);
        this.timerGroup.add(this.timerOff);
        this.legalMovesGroup = new ButtonGroup();
        this.legalMovesGroup.add(this.legalMovesOn);
        this.legalMovesGroup.add(this.legalMovesOff);
        this.highlighterGroup = new ButtonGroup();
        this.highlighterGroup.add(this.highlighterOn);
        this.highlighterGroup.add(this.highlighterOff);
        this.conflictGroup = new ButtonGroup();
        this.conflictGroup.add(this.conflictOn);
        this.conflictGroup.add(this.conflictOff);
        this.hintsGroup = new ButtonGroup();
        this.hintsGroup.add(this.hintsOn);
        this.hintsGroup.add(this.hintsOff);
        this.solutionsgroup = new ButtonGroup();
        this.solutionsgroup.add(this.solutionsOn);
        this.solutionsgroup.add(this.solutionsOff);
        this.initializeSettings();

        /* Asks user if they're sure when closing the window */
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                if (WindowUtility.askYesNo("Are you sure you want to quit?",
                        "Quitting")) {
                    FileUtility.saveSettings();
                    FileUtility.saveBestTimes();
                    System.exit(0);
                }
            }
        });

        this.setVisible(true);
    }


    /***/
    private void initializeSettings() {
        FileUtility.loadSettings();
        if (Settings.showTimer())
            this.timerOn.setSelected(true);
        else
            this.timerOff.setSelected(true);
        if (Settings.showLegal())
            this.legalMovesOn.setSelected(true);
        else
            this.legalMovesOff.setSelected(true);
        if (Settings.showHighlighted())
            this.highlighterOn.setSelected(true);
        else
            this.highlighterOff.setSelected(true);
        if (Settings.showConflictingNumbers())
            this.conflictOn.setSelected(true);
        else
            this.conflictOff.setSelected(true);
        if (Settings.showHints())
            this.hintsOn.setSelected(true);
        else
            this.hintsOff.setSelected(true);
        if (Settings.showSolutions())
            this.solutionsOn.setSelected(true);
        else
            this.solutionsOff.setSelected(true);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        timerOn = new javax.swing.JRadioButton();
        timerOff = new javax.swing.JRadioButton();
        legalMovesOn = new javax.swing.JRadioButton();
        legalMovesOff = new javax.swing.JRadioButton();
        highlighterOn = new javax.swing.JRadioButton();
        highlighterOff = new javax.swing.JRadioButton();
        conflictOn = new javax.swing.JRadioButton();
        conflictOff = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        hintsOn = new javax.swing.JRadioButton();
        hintsOff = new javax.swing.JRadioButton();
        solutionsOn = new javax.swing.JRadioButton();
        solutionsOff = new javax.swing.JRadioButton();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        backButton.setBackground(new java.awt.Color(153, 153, 153));
        backButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Settings");

        timerOn.setBackground(new java.awt.Color(255, 255, 204));
        timerOn.setText("On");
        timerOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timerOnActionPerformed(evt);
            }
        });

        timerOff.setBackground(new java.awt.Color(255, 255, 204));
        timerOff.setText("Off");
        timerOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timerOffActionPerformed(evt);
            }
        });

        legalMovesOn.setBackground(new java.awt.Color(255, 255, 204));
        legalMovesOn.setText("On");
        legalMovesOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legalMovesOnActionPerformed(evt);
            }
        });

        legalMovesOff.setBackground(new java.awt.Color(255, 255, 204));
        legalMovesOff.setText("Off");
        legalMovesOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legalMovesOffActionPerformed(evt);
            }
        });

        highlighterOn.setBackground(new java.awt.Color(255, 255, 204));
        highlighterOn.setText("On");
        highlighterOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlighterOnActionPerformed(evt);
            }
        });

        highlighterOff.setBackground(new java.awt.Color(255, 255, 204));
        highlighterOff.setText("Off");
        highlighterOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlighterOffActionPerformed(evt);
            }
        });

        conflictOn.setBackground(new java.awt.Color(255, 255, 204));
        conflictOn.setText("On");
        conflictOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conflictOnActionPerformed(evt);
            }
        });

        conflictOff.setBackground(new java.awt.Color(255, 255, 204));
        conflictOff.setText("Off");
        conflictOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conflictOffActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Show Timer:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Show Legal Moves:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Double-Click to Highlight:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Show Illegal Numbers:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Enable Hints:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Enable Solutions:");

        hintsOn.setBackground(new java.awt.Color(255, 255, 204));
        hintsOn.setText("On");
        hintsOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintsOnActionPerformed(evt);
            }
        });

        hintsOff.setBackground(new java.awt.Color(255, 255, 204));
        hintsOff.setText("Off");
        hintsOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintsOffActionPerformed(evt);
            }
        });

        solutionsOn.setBackground(new java.awt.Color(255, 255, 204));
        solutionsOn.setText("On");
        solutionsOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solutionsOnActionPerformed(evt);
            }
        });

        solutionsOff.setBackground(new java.awt.Color(255, 255, 204));
        solutionsOff.setText("Off");
        solutionsOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solutionsOffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(backButton)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(conflictOn)
                                .addGap(18, 18, 18)
                                .addComponent(conflictOff))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(timerOn)
                                .addGap(18, 18, 18)
                                .addComponent(timerOff))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(highlighterOn)
                                .addGap(18, 18, 18)
                                .addComponent(highlighterOff))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(legalMovesOn)
                                .addGap(18, 18, 18)
                                .addComponent(legalMovesOff))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(solutionsOn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(hintsOn)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hintsOff)
                            .addComponent(solutionsOff))))
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timerOn)
                            .addComponent(timerOff)
                            .addComponent(jLabel7))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(legalMovesOn)
                        .addComponent(legalMovesOff))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(highlighterOn)
                    .addComponent(highlighterOff)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conflictOn)
                    .addComponent(conflictOff)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(hintsOn)
                    .addComponent(hintsOff))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(solutionsOn)
                    .addComponent(solutionsOff))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

    // <editor-fold defaultstate="collapsed" desc="Radio Button Aciton Event Handlers">
    /* Goes back to the main menu */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        FileUtility.saveSettings();
        MainFrame f = new MainFrame(this.getX(), this.getY());
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed
    /* Makes the timer visible */
    private void timerOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timerOnActionPerformed
        Settings.showTimer(true);
    }//GEN-LAST:event_timerOnActionPerformed
    /* Makes the timer invisible */
    private void timerOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timerOffActionPerformed
        Settings.showTimer(false);
    }//GEN-LAST:event_timerOffActionPerformed
    /* Makes legal moves pane visible */
    private void legalMovesOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legalMovesOnActionPerformed
        Settings.showLegal(true);
    }//GEN-LAST:event_legalMovesOnActionPerformed
    /* Makes legal moves pane invisible */
    private void legalMovesOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legalMovesOffActionPerformed
        Settings.showLegal(false);
    }//GEN-LAST:event_legalMovesOffActionPerformed
    /* Turns highlighter on */
    private void highlighterOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlighterOnActionPerformed
        Settings.showHighlighted(true);
    }//GEN-LAST:event_highlighterOnActionPerformed
    /* Turns highlighter off */
    private void highlighterOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlighterOffActionPerformed
        Settings.showHighlighted(false);
    }//GEN-LAST:event_highlighterOffActionPerformed
    /* Turns illegal number highlighter on */
    private void conflictOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conflictOnActionPerformed
        Settings.showConflictingNumbers(true);
    }//GEN-LAST:event_conflictOnActionPerformed
    /* Turns illegal number highlighter off */
    private void conflictOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conflictOffActionPerformed
        Settings.showConflictingNumbers(false);
    }//GEN-LAST:event_conflictOffActionPerformed
    /* Turns hints on */
    private void hintsOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintsOnActionPerformed
        Settings.showHints(true);
    }//GEN-LAST:event_hintsOnActionPerformed
    /* Turns hints off */
    private void hintsOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintsOffActionPerformed
        Settings.showHints(false);
    }//GEN-LAST:event_hintsOffActionPerformed
    /* Turns solutions on */
    private void solutionsOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solutionsOnActionPerformed
        Settings.showSolutions(true);
    }//GEN-LAST:event_solutionsOnActionPerformed
    /* Turns solutions off */
    private void solutionsOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solutionsOffActionPerformed
        Settings.showSolutions(false);
    }//GEN-LAST:event_solutionsOffActionPerformed
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Component Declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JRadioButton conflictOff;
    private javax.swing.JRadioButton conflictOn;
    private javax.swing.JRadioButton highlighterOff;
    private javax.swing.JRadioButton highlighterOn;
    private javax.swing.JRadioButton hintsOff;
    private javax.swing.JRadioButton hintsOn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton legalMovesOff;
    private javax.swing.JRadioButton legalMovesOn;
    private javax.swing.JRadioButton solutionsOff;
    private javax.swing.JRadioButton solutionsOn;
    private javax.swing.JRadioButton timerOff;
    private javax.swing.JRadioButton timerOn;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

} // End SettingsFrame class