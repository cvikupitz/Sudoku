/**
 * Main.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains the main method that is invoked when the user starts the program.
 */
package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// functionality is not architectural
public class Main {

    /* Invoked at runtime, starts and runs the program. */
    public static void main(String[] args) throws FileNotFoundException, IOException {

//        BufferedReader r = new BufferedReader(new FileReader("5.txt"));
//        String l = r.readLine();
//        int i = 0, j = 0;
//        while (l != null) {
//            SudokuPuzzle p = new SudokuPuzzle(l);
//            i++;
//            if (p.isComplete())
//                j++;
//            l = r.readLine();
//        }
//        System.out.printf("%d puzzles scanned. %d complete.\n", i, j);


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
            java.util.logging.Logger.getLogger(SudokuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SudokuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SudokuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SudokuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        SudokuPuzzle p = new SudokuPuzzle("571390240300850009080010035043205001260041750005600820004123500152008096007009410");
        p.print();
        System.out.printf("\nIs Complete: %s\n\n", p.isComplete());
        SudokuFrame f = new SudokuFrame(p.toArray());
    }

}
