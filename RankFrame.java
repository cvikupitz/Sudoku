/**
 * RankFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Class that contains a GUI for displaying the user's best puzzle completion times.
 * Contains a table that organizes the top 10 times by puzzle difficulty. Also
 * contains a button for clearing the best times, and a back button for returning
 * to the MainFrame.
 */
package sudoku;


/* Imports */
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

public class RankFrame extends JFrame {

    /* Default constructor */
    public RankFrame(int x, int y) {

        /* Initialize components */
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sudoku_icon.png")));
        this.setTitle("Sudoku");
        this.setLocation(x, y);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* Sets the table's headers and column width sizes */
        this.noviceTable.getTableHeader().setReorderingAllowed(false);
        this.noviceTable.getColumn("Rank").setMinWidth(75);
        this.noviceTable.getColumn("Rank").setMaxWidth(75);
        this.noviceTable.getColumn("Date").setMinWidth(225);
        this.noviceTable.getColumn("Date").setMaxWidth(225);
        this.easyTable.getTableHeader().setReorderingAllowed(false);
        this.easyTable.getColumn("Rank").setMinWidth(75);
        this.easyTable.getColumn("Rank").setMaxWidth(75);
        this.easyTable.getColumn("Date").setMinWidth(225);
        this.easyTable.getColumn("Date").setMaxWidth(225);
        this.mediumTable.getTableHeader().setReorderingAllowed(false);
        this.mediumTable.getColumn("Rank").setMinWidth(75);
        this.mediumTable.getColumn("Rank").setMaxWidth(75);
        this.mediumTable.getColumn("Date").setMinWidth(225);
        this.mediumTable.getColumn("Date").setMaxWidth(225);
        this.hardTable.getTableHeader().setReorderingAllowed(false);
        this.hardTable.getColumn("Rank").setMinWidth(75);
        this.hardTable.getColumn("Rank").setMaxWidth(75);
        this.hardTable.getColumn("Date").setMinWidth(225);
        this.hardTable.getColumn("Date").setMaxWidth(225);
        this.expertTable.getTableHeader().setReorderingAllowed(false);
        this.expertTable.getColumn("Rank").setMinWidth(75);
        this.expertTable.getColumn("Rank").setMaxWidth(75);
        this.expertTable.getColumn("Date").setMinWidth(225);
        this.expertTable.getColumn("Date").setMaxWidth(225);

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

        this.bindToTable();
        this.setVisible(true);
    }


    /***/
    private void bindToTable() {
        int i = 0;
        for (HighScoreNode n : BestTimes.novice) {
            this.noviceTable.setValueAt(BestTimes.timeToString(n.getScore()), i, 1);
            this.noviceTable.setValueAt(n.getDate(), i, 2);
            i++;
        }
        i = 0;
        for (HighScoreNode n : BestTimes.easy) {
            this.easyTable.setValueAt(BestTimes.timeToString(n.getScore()), i, 1);
            this.easyTable.setValueAt(n.getDate(), i, 2);
            i++;
        }
        for (HighScoreNode n : BestTimes.medium) {
            this.mediumTable.setValueAt(BestTimes.timeToString(n.getScore()), i, 1);
            this.mediumTable.setValueAt(n.getDate(), i, 2);
            i++;
        }
        i = 0;
        for (HighScoreNode n : BestTimes.hard) {
            this.hardTable.setValueAt(BestTimes.timeToString(n.getScore()), i, 1);
            this.hardTable.setValueAt(n.getDate(), i, 2);
            i++;
        }
        i = 0;
        for (HighScoreNode n : BestTimes.expert) {
            this.expertTable.setValueAt(BestTimes.timeToString(n.getScore()), i, 1);
            this.expertTable.setValueAt(n.getDate(), i, 2);
            i++;
        }
    }


    /***/
    private void reset() {
        if (!WindowUtility.askYesNo("You are about to delete all your best times for this difficulty.\n"
                + "Are your sure you want to reset?", "Warning!"))
            return;
        int i = this.timesTable.getSelectedIndex();
        if (i == -1)
            return;

        switch (i) {
            case 0:
                BestTimes.novice.clear();
                for (int j = 0; j < 10; j++) {
                    this.noviceTable.setValueAt("", j, 1);
                    this.noviceTable.setValueAt("", j, 2);
                }
                break;
            case 1:
                BestTimes.easy.clear();
                for (int j = 0; j < 10; j++) {
                    this.easyTable.setValueAt("", j, 1);
                    this.easyTable.setValueAt("", j, 2);
                }
                break;
            case 2:
                BestTimes.medium.clear();
                for (int j = 0; j < 10; j++) {
                    this.mediumTable.setValueAt("", j, 1);
                    this.mediumTable.setValueAt("", j, 2);
                }
                break;
            case 3:
                BestTimes.hard.clear();
                for (int j = 0; j < 10; j++) {
                    this.hardTable.setValueAt("", j, 1);
                    this.hardTable.setValueAt("", j, 2);
                }
                break;
            default:
                BestTimes.expert.clear();
                for (int j = 0; j < 10; j++) {
                    this.expertTable.setValueAt("", j, 1);
                    this.expertTable.setValueAt("", j, 2);
                }
                break;
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

        jPanel2 = new javax.swing.JPanel();
        timesTable = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        noviceTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        easyTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        mediumTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        hardTable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        expertTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        timesTable.setFocusable(false);
        timesTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        noviceTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        noviceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null},
                {"2", null, null},
                {"3", null, null},
                {"4", null, null},
                {"5", null, null},
                {"6", null, null},
                {"7", null, null},
                {"8", null, null},
                {"9", null, null},
                {"10", null, null}
            },
            new String [] {
                "Rank", "Time", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        noviceTable.setEnabled(false);
        noviceTable.setFocusable(false);
        jScrollPane1.setViewportView(noviceTable);

        timesTable.addTab("Novice", jScrollPane1);

        easyTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        easyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "", null},
                {"2", "", null},
                {"3", "", null},
                {"4", "", null},
                {"5", "", null},
                {"6", "", null},
                {"7", "", null},
                {"8", "", null},
                {"9", "", ""},
                {"10", "", null}
            },
            new String [] {
                "Rank", "Time", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        easyTable.setEnabled(false);
        jScrollPane2.setViewportView(easyTable);

        timesTable.addTab("Easy", jScrollPane2);

        mediumTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mediumTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null},
                {"2", null, null},
                {"3", null, null},
                {"4", null, null},
                {"5", null, null},
                {"6", null, null},
                {"7", null, null},
                {"8", null, null},
                {"9", null, null},
                {"10", null, null}
            },
            new String [] {
                "Rank", "Time", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mediumTable.setEnabled(false);
        jScrollPane3.setViewportView(mediumTable);

        timesTable.addTab("Medium", jScrollPane3);

        hardTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        hardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null},
                {"2", null, null},
                {"3", null, null},
                {"4", null, null},
                {"5", null, null},
                {"6", null, null},
                {"7", null, null},
                {"8", null, null},
                {"9", null, null},
                {"10", null, null}
            },
            new String [] {
                "Rank", "Time", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        hardTable.setEnabled(false);
        jScrollPane4.setViewportView(hardTable);

        timesTable.addTab("Hard", jScrollPane4);

        expertTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        expertTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null},
                {"2", null, null},
                {"3", null, null},
                {"4", null, null},
                {"5", null, null},
                {"6", null, null},
                {"7", null, null},
                {"8", null, null},
                {"9", null, null},
                {"10", null, null}
            },
            new String [] {
                "Rank", "Time", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        expertTable.setEnabled(false);
        jScrollPane5.setViewportView(expertTable);

        timesTable.addTab("Expert", jScrollPane5);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Best Times");

        resetButton.setBackground(new java.awt.Color(255, 153, 153));
        resetButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(153, 153, 153));
        backButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(backButton)
                                .addGap(310, 310, 310)
                                .addComponent(resetButton))
                            .addComponent(timesTable, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(timesTable, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Button Action Event Handlers">
    /* Returns to the main menu */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        MainFrame m = new MainFrame(this.getX(), this.getY());
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed
    /* Resets the data in the table */
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        this.reset();
    }//GEN-LAST:event_resetButtonActionPerformed
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Component Declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JTable easyTable;
    private javax.swing.JTable expertTable;
    private javax.swing.JTable hardTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable mediumTable;
    private javax.swing.JTable noviceTable;
    private javax.swing.JButton resetButton;
    private javax.swing.JTabbedPane timesTable;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

} // End RankFrame class