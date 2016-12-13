/**
 * AchievementsFrame.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class AchievementsFrame extends JFrame {

    /* Default constructor */
    public AchievementsFrame() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(
                "icons/sudoku_icon.png")));

        this.updateAll();
    }


    private void beginner() {
        int VALUES[] = {1, 10, 30, 75, 150, 250};
        JLabel STATUS[] = {this.a1Status, this.a2Status, this.a3Status,
                           this.a4Status, this.a5Status, this.a6Status};
        JLabel ICONS[] = {this.a1Icon, this.a2Icon, this.a3Icon, this.a4Icon,
                          this.a5Icon, this.a6Icon};

        for (int i = 0; i < 6; i++) {
            if (Achievements.puzzles[5] < VALUES[i]) {
                STATUS[i].setText(String.format("(%d/%d)", Achievements.puzzles[5], VALUES[i]));
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/x_icon.png"))));
            } else {
                STATUS[i].setText("");
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/check_icon.png"))));
            }
        }
    }


    private void enthusiast() {
        JLabel STATUS[] = {this.b1Status, this.b2Status, this.b3Status,
                           this.b4Status, this.b5Status};
        JLabel ICONS[] = {this.b1Icon, this.b2Icon, this.b3Icon, this.b4Icon,
                          this.b5Icon};

        for (int i = 0; i < 5; i++) {
            if (Achievements.puzzles[i] < 100) {
                STATUS[i].setText(String.format("(%d/100)", Achievements.puzzles[i]));
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/x_icon.png"))));
            } else {
                STATUS[i].setText("");
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/check_icon.png"))));
            }
        }
    }


    private void competitive() {
        JLabel ICONS[] = {this.c1Icon, this.c2Icon, this.c3Icon, this.c4Icon,
                          this.c5Icon};

        for (int i = 0; i < 5; i++) {
            if (!Achievements.competitive[i])
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/x_icon.png"))));
            else
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/check_icon.png"))));
        }
    }


    private void handicapped() {
        JLabel STATUS[] = {this.d1Status, this.d2Status, this.d3Status,
                           this.d4Status, this.d5Status};
        JLabel ICONS[] = {this.d1Icon, this.d2Icon, this.d3Icon, this.d4Icon,
                          this.d5Icon};

        for (int i = 0; i < 5; i++) {
            if (Achievements.handicapped[i] < 30) {
                STATUS[i].setText(String.format("(%d/30)", Achievements.handicapped[i]));
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/x_icon.png"))));
            } else {
                STATUS[i].setText("");
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/check_icon.png"))));
            }
        }
    }


    private void assistant() {
        if (Achievements.assistant[0] < 30) {
            this.e1Status.setText(String.format("(%d/30)", Achievements.assistant[0]));
            this.e1Icon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/x_icon.png"))));
        } else {
            this.e1Status.setText("");
            this.e1Icon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/check_icon.png"))));
        }

        if (Achievements.assistant[1] < 30) {
            this.e2Status.setText(String.format("(%d/30)", Achievements.assistant[1]));
            this.e2Icon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/x_icon.png"))));
        } else {
            this.e2Status.setText("");
            this.e2Icon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/check_icon.png"))));
        }
    }


    private void engineer() {
        JLabel ICONS[] = {this.f1Icon, this.f2Icon, this.f3Icon, this.f4Icon,
                          this.f5Icon, this.f6Icon, this.f7Icon};

        for (int i = 0; i < 7; i++) {
            if (!Achievements.engineer[i])
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/x_icon.png"))));
            else
                ICONS[i].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource("icons/check_icon.png"))));
        }
    }


    private void updateAll() {
        this.beginner();
        this.enthusiast();
        this.competitive();
        this.handicapped();
        this.assistant();
        this.engineer();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel33 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();
        beginner = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        a1Status = new javax.swing.JLabel();
        a2Status = new javax.swing.JLabel();
        a3Status = new javax.swing.JLabel();
        a4Status = new javax.swing.JLabel();
        a5Status = new javax.swing.JLabel();
        a6Status = new javax.swing.JLabel();
        a1Icon = new javax.swing.JLabel();
        a2Icon = new javax.swing.JLabel();
        a3Icon = new javax.swing.JLabel();
        a4Icon = new javax.swing.JLabel();
        a5Icon = new javax.swing.JLabel();
        a6Icon = new javax.swing.JLabel();
        enthusiast = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        b1Status = new javax.swing.JLabel();
        b2Status = new javax.swing.JLabel();
        b3Status = new javax.swing.JLabel();
        b4Status = new javax.swing.JLabel();
        b5Status = new javax.swing.JLabel();
        b1Icon = new javax.swing.JLabel();
        b2Icon = new javax.swing.JLabel();
        b3Icon = new javax.swing.JLabel();
        b4Icon = new javax.swing.JLabel();
        b5Icon = new javax.swing.JLabel();
        competitive = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        c1Icon = new javax.swing.JLabel();
        c2Icon = new javax.swing.JLabel();
        c3Icon = new javax.swing.JLabel();
        c4Icon = new javax.swing.JLabel();
        c5Icon = new javax.swing.JLabel();
        handicapped = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        d1Status = new javax.swing.JLabel();
        d2Status = new javax.swing.JLabel();
        d3Status = new javax.swing.JLabel();
        d4Status = new javax.swing.JLabel();
        d5Status = new javax.swing.JLabel();
        d1Icon = new javax.swing.JLabel();
        d2Icon = new javax.swing.JLabel();
        d3Icon = new javax.swing.JLabel();
        d4Icon = new javax.swing.JLabel();
        d5Icon = new javax.swing.JLabel();
        assistant = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        e1Status = new javax.swing.JLabel();
        e2Status = new javax.swing.JLabel();
        e1Icon = new javax.swing.JLabel();
        e2Icon = new javax.swing.JLabel();
        engineer = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        f1Icon = new javax.swing.JLabel();
        f2Icon = new javax.swing.JLabel();
        f3Icon = new javax.swing.JLabel();
        f4Icon = new javax.swing.JLabel();
        f5Icon = new javax.swing.JLabel();
        f6Icon = new javax.swing.JLabel();
        f7Icon = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel33.setText("jLabel33");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setLocation(new java.awt.Point(300, 200));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Achivements");

        tabbedPane.setBackground(new java.awt.Color(255, 255, 204));
        tabbedPane.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        beginner.setBackground(new java.awt.Color(255, 255, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Complete a puzzle.");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Complete 10 puzzles.");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Complete 30 puzzles.");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Complete 75 puzzles.");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Complete 150 puzzles.");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Complete 250 puzzles.");

        a1Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        a1Status.setText("--");

        a2Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        a2Status.setText("--");

        a3Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        a3Status.setText("--");

        a4Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        a4Status.setText("--");

        a5Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        a5Status.setText("--");

        a6Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        a6Status.setText("--");

        a1Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        a2Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        a3Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        a4Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        a5Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        a6Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout beginnerLayout = new javax.swing.GroupLayout(beginner);
        beginner.setLayout(beginnerLayout);
        beginnerLayout.setHorizontalGroup(
            beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beginnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(beginnerLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a1Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a1Icon))
                    .addGroup(beginnerLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a6Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 343, Short.MAX_VALUE)
                        .addComponent(a6Icon))
                    .addGroup(beginnerLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a2Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a2Icon))
                    .addGroup(beginnerLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a3Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a3Icon))
                    .addGroup(beginnerLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a4Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a4Icon))
                    .addGroup(beginnerLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a5Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(a5Icon)))
                .addContainerGap())
        );
        beginnerLayout.setVerticalGroup(
            beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beginnerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(a1Status)
                    .addComponent(a1Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(a2Status)
                    .addComponent(a2Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(a3Status)
                    .addComponent(a3Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(a4Status)
                    .addComponent(a4Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(a5Status)
                    .addComponent(a5Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(beginnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(a6Status)
                    .addComponent(a6Icon))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Beginner", beginner);

        enthusiast.setBackground(new java.awt.Color(255, 255, 204));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Complete 100 novice puzzles.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Complete 100 easy puzzles.");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Complete 100 medium puzzles.");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Complete 100 hard puzzles.");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Complete 100 expert puzzles.");

        b1Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b1Status.setText("--");

        b2Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b2Status.setText("--");

        b3Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b3Status.setText("--");

        b4Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b4Status.setText("--");

        b5Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        b5Status.setText("--");

        b1Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        b2Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        b3Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        b4Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        b5Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout enthusiastLayout = new javax.swing.GroupLayout(enthusiast);
        enthusiast.setLayout(enthusiastLayout);
        enthusiastLayout.setHorizontalGroup(
            enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enthusiastLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(enthusiastLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b1Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b1Icon))
                    .addGroup(enthusiastLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b5Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b5Icon))
                    .addGroup(enthusiastLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b2Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b2Icon))
                    .addGroup(enthusiastLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b3Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                        .addComponent(b3Icon))
                    .addGroup(enthusiastLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b4Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b4Icon)))
                .addContainerGap())
        );
        enthusiastLayout.setVerticalGroup(
            enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enthusiastLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(b1Status)
                    .addComponent(b1Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(b2Status)
                    .addComponent(b2Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(b3Status)
                    .addComponent(b3Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(b4Status)
                    .addComponent(b4Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(enthusiastLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(b5Status)
                    .addComponent(b5Icon))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Enthusiast", enthusiast);

        competitive.setBackground(new java.awt.Color(255, 255, 204));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Complete a novice puzzle in 50 seconds.");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Complete an easy puzzle in 1 minute.");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Complete a medium puzzle in 1 minute, 15 seconds.");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Complete a hard puzzle in 1 minute, 45 seconds.");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Complete an expert puzzle in 3 minutes.");

        c1Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        c2Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        c3Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        c4Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        c4Icon.setToolTipText("");

        c5Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout competitiveLayout = new javax.swing.GroupLayout(competitive);
        competitive.setLayout(competitiveLayout);
        competitiveLayout.setHorizontalGroup(
            competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(competitiveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(competitiveLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(c1Icon))
                    .addGroup(competitiveLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(c5Icon))
                    .addGroup(competitiveLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(c2Icon))
                    .addGroup(competitiveLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
                        .addComponent(c3Icon))
                    .addGroup(competitiveLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(c4Icon)))
                .addContainerGap())
        );
        competitiveLayout.setVerticalGroup(
            competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(competitiveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(c1Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(c2Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(c3Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(c4Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(competitiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(c5Icon))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Competitive", competitive);

        handicapped.setBackground(new java.awt.Color(255, 255, 204));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Complete 30 novice puzzles with all settings disabled.");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Complete 30 easy puzzles with all settings disabled.");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Complete 30 medium puzzles with all settings disabled.");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Complete 30 hard puzzles with all settings disabled.");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Complete 30 expert puzzles with all settings disabled.");

        d1Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d1Status.setText("--");

        d2Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d2Status.setText("--");

        d3Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d3Status.setText("--");

        d4Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d4Status.setText("--");

        d5Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d5Status.setText("--");

        d1Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        d2Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        d3Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        d4Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        d5Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout handicappedLayout = new javax.swing.GroupLayout(handicapped);
        handicapped.setLayout(handicappedLayout);
        handicappedLayout.setHorizontalGroup(
            handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handicappedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handicappedLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(d1Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(d1Icon))
                    .addGroup(handicappedLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(d5Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(d5Icon))
                    .addGroup(handicappedLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(d2Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(d2Icon))
                    .addGroup(handicappedLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(d3Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                        .addComponent(d3Icon))
                    .addGroup(handicappedLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(d4Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(d4Icon)))
                .addContainerGap())
        );
        handicappedLayout.setVerticalGroup(
            handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handicappedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(d1Status)
                    .addComponent(d1Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(d2Status)
                    .addComponent(d2Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(d3Status)
                    .addComponent(d3Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(d4Status)
                    .addComponent(d4Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handicappedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(d5Status)
                    .addComponent(d5Icon))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Handicapped", handicapped);

        assistant.setBackground(new java.awt.Color(255, 255, 204));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Request for a puzzle hint 30 times.");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Request for a puzzle's solution 30 times.");

        e1Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        e1Status.setText("--");

        e2Status.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        e2Status.setText("--");

        e1Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        e2Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout assistantLayout = new javax.swing.GroupLayout(assistant);
        assistant.setLayout(assistantLayout);
        assistantLayout.setHorizontalGroup(
            assistantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assistantLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(assistantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assistantLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(e1Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(e1Icon))
                    .addGroup(assistantLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(e2Status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                        .addComponent(e2Icon)))
                .addContainerGap())
        );
        assistantLayout.setVerticalGroup(
            assistantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assistantLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(assistantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(e1Status)
                    .addComponent(e1Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(assistantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(e2Status)
                    .addComponent(e2Icon))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Assistant", assistant);

        engineer.setBackground(new java.awt.Color(255, 255, 204));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Create a customized puzzle.");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Complete a novice customized puzzle.");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Complete an easy customized puzzle.");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Complete a medium customized puzzle.");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Complete a hard customized puzzle.");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Complete an expert customized puzzle.");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Complete an empty customized puzzle.");

        f1Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        f2Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        f3Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        f4Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        f5Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        f6Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        f7Icon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout engineerLayout = new javax.swing.GroupLayout(engineer);
        engineer.setLayout(engineerLayout);
        engineerLayout.setHorizontalGroup(
            engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(engineerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(engineerLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(f1Icon))
                    .addGroup(engineerLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(f7Icon))
                    .addGroup(engineerLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(f2Icon))
                    .addGroup(engineerLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(f3Icon))
                    .addGroup(engineerLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                        .addComponent(f4Icon))
                    .addGroup(engineerLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(f5Icon))
                    .addGroup(engineerLayout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(f6Icon)))
                .addContainerGap())
        );
        engineerLayout.setVerticalGroup(
            engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(engineerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(f1Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(f2Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(f3Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(f4Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(f5Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(f6Icon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(engineerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(f7Icon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Engineer", engineer);

        jButton1.setBackground(new java.awt.Color(153, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Back");

        jButton2.setBackground(new java.awt.Color(255, 153, 153));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Reset");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPane))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
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
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AchievementsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AchievementsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AchievementsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AchievementsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AchievementsFrame().setVisible(true);
            }
        });
    }


    // <editor-fold defaultstate="collapsed" desc="Component Declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel a1Icon;
    private javax.swing.JLabel a1Status;
    private javax.swing.JLabel a2Icon;
    private javax.swing.JLabel a2Status;
    private javax.swing.JLabel a3Icon;
    private javax.swing.JLabel a3Status;
    private javax.swing.JLabel a4Icon;
    private javax.swing.JLabel a4Status;
    private javax.swing.JLabel a5Icon;
    private javax.swing.JLabel a5Status;
    private javax.swing.JLabel a6Icon;
    private javax.swing.JLabel a6Status;
    private javax.swing.JPanel assistant;
    private javax.swing.JLabel b1Icon;
    private javax.swing.JLabel b1Status;
    private javax.swing.JLabel b2Icon;
    private javax.swing.JLabel b2Status;
    private javax.swing.JLabel b3Icon;
    private javax.swing.JLabel b3Status;
    private javax.swing.JLabel b4Icon;
    private javax.swing.JLabel b4Status;
    private javax.swing.JLabel b5Icon;
    private javax.swing.JLabel b5Status;
    private javax.swing.JPanel beginner;
    private javax.swing.JLabel c1Icon;
    private javax.swing.JLabel c2Icon;
    private javax.swing.JLabel c3Icon;
    private javax.swing.JLabel c4Icon;
    private javax.swing.JLabel c5Icon;
    private javax.swing.JPanel competitive;
    private javax.swing.JLabel d1Icon;
    private javax.swing.JLabel d1Status;
    private javax.swing.JLabel d2Icon;
    private javax.swing.JLabel d2Status;
    private javax.swing.JLabel d3Icon;
    private javax.swing.JLabel d3Status;
    private javax.swing.JLabel d4Icon;
    private javax.swing.JLabel d4Status;
    private javax.swing.JLabel d5Icon;
    private javax.swing.JLabel d5Status;
    private javax.swing.JLabel e1Icon;
    private javax.swing.JLabel e1Status;
    private javax.swing.JLabel e2Icon;
    private javax.swing.JLabel e2Status;
    private javax.swing.JPanel engineer;
    private javax.swing.JPanel enthusiast;
    private javax.swing.JLabel f1Icon;
    private javax.swing.JLabel f2Icon;
    private javax.swing.JLabel f3Icon;
    private javax.swing.JLabel f4Icon;
    private javax.swing.JLabel f5Icon;
    private javax.swing.JLabel f6Icon;
    private javax.swing.JLabel f7Icon;
    private javax.swing.JPanel handicapped;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>

} // End AchievementsFrame class