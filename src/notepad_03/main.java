/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad_03;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class main {

    public static void main(String[] args) {
        text Frame = new text();
        Frame.setLocation(300, 105);
        
        Frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
               
                int n=3;
                if (text.save == 0) {
                    n = JOptionPane.showOptionDialog(null,"Do you like " + "to save the changes?",
                            "Notepad",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            text.options,
                            text.options[2]);

                    System.out.println(" N = " + n);
                    if (n == 0) {
                        JFileChooser filechooser = new JFileChooser();
                        int r = filechooser.showSaveDialog(null);
                        if (r != JFileChooser.CANCEL_OPTION) {
                            text.saveFile(filechooser.getSelectedFile());

                        }
                    }
                }
                System.exit(0);
            }
        });
        Frame.setSize(700, 500); // set frame size
        Frame.setVisible(true);
    }
}
