/**
 * Main.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * Contains the main method that is invoked when the user starts the program.
 */
package sudoku;


/* Imports */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

// functionality is not architectural
public class Main {

    /* Invoked at runtime, starts and runs the program. */
    public static void main(String[] args) throws IOException {

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

        /* Loads the Sudoku save folder on user's computer */
        File dir = new File(FileUtility.PATH);
        if (!dir.exists())
            dir.mkdir();

        SudokuPuzzle p = FileUtility.loadGame();
        if (p == null)
            p = getPuzzle();
        SudokuFrame f = new SudokuFrame(p);
    }


    ////////////////////////////////////////////////////////////////////////
    protected static SudokuPuzzle getPuzzle() {
        Random r = new Random();
        int k = r.nextInt(10000);
        try (BufferedReader br = new BufferedReader(new FileReader("3.txt"))) {
            String line = br.readLine();
            for (int i = 0; i < k-1; i++)
                line = br.readLine();
            SudokuPuzzle p = new SudokuPuzzle(line);
            br.close();
            return p;

        } catch (Exception e) {return null;}
    }
    //////////////////////////////////////////////////////////////////////////
}


/*
0.txt - 45
1.txt - 40
2.txt - 35
3.txt - 30
5.txt - 25
*/
