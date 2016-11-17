/**
 * RankFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

public class RankFrame extends JFrame {

    public RankFrame(int x, int y) {

        /* FIXME */
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("sudoku_icon.png")));
        this.setTitle("Sudoku");
        this.setLocation(x, y);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* FIXME */
        this.noviceTable.getTableHeader().setReorderingAllowed(false);
        this.noviceTable.getColumn("Rank").setMinWidth(100);
        this.noviceTable.getColumn("Rank").setMaxWidth(100);
        this.noviceTable.getColumn("Date").setMinWidth(200);
        this.noviceTable.getColumn("Date").setMaxWidth(200);
        this.easyTable.getTableHeader().setReorderingAllowed(false);
        this.easyTable.getColumn("Rank").setMinWidth(100);
        this.easyTable.getColumn("Rank").setMaxWidth(100);
        this.easyTable.getColumn("Date").setMinWidth(200);
        this.easyTable.getColumn("Date").setMaxWidth(200);
        this.mediumTable.getTableHeader().setReorderingAllowed(false);
        this.mediumTable.getColumn("Rank").setMinWidth(100);
        this.mediumTable.getColumn("Rank").setMaxWidth(100);
        this.mediumTable.getColumn("Date").setMinWidth(200);
        this.mediumTable.getColumn("Date").setMaxWidth(200);
        this.hardTable.getTableHeader().setReorderingAllowed(false);
        this.hardTable.getColumn("Rank").setMinWidth(100);
        this.hardTable.getColumn("Rank").setMaxWidth(100);
        this.hardTable.getColumn("Date").setMinWidth(200);
        this.hardTable.getColumn("Date").setMaxWidth(200);
        this.expertTable.getTableHeader().setReorderingAllowed(false);
        this.expertTable.getColumn("Rank").setMinWidth(100);
        this.expertTable.getColumn("Rank").setMaxWidth(100);
        this.expertTable.getColumn("Date").setMinWidth(200);
        this.expertTable.getColumn("Date").setMaxWidth(200);

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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        hardTabbedPane = new javax.swing.JTabbedPane();
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

        hardTabbedPane.setFocusable(false);
        hardTabbedPane.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

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

        hardTabbedPane.addTab("Novice", jScrollPane1);

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

        hardTabbedPane.addTab("Easy", jScrollPane2);

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

        hardTabbedPane.addTab("Medium", jScrollPane3);

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

        hardTabbedPane.addTab("Hard", jScrollPane4);

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

        hardTabbedPane.addTab("Expert", jScrollPane5);

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
                            .addComponent(hardTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(hardTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(backButton))
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

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        MainFrame m = new MainFrame(this.getX(), this.getY());
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JTable easyTable;
    private javax.swing.JTable expertTable;
    private javax.swing.JTabbedPane hardTabbedPane;
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
    // End of variables declaration//GEN-END:variables
}
