
package notepad_03;

import javax.swing.*;
import java.awt.BorderLayout.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.BadLocationException;

public class bottom_menu extends JPanel
{

    public static int suru = 0, sash, all = 0;
    public static JTextField et;
    public JButton b1, b2, b3;
    public static JCheckBox c;

    public bottom_menu()
      {
        ItemHandler itemHandler = new ItemHandler();
        et = new JTextField(10);
        b1 = new JButton();
        Font f = new Font("Courier New", 0, 10);
        JLabel l2 = new JLabel("x");
        l2.setFont(f);
        b1.add(l2);
        // b1.setSize(50, 50);
        Font fc = new Font("Courier New", 0, 20);
        JLabel l = new JLabel("  Find : ");
        l.setFont(fc);
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        //      this.setAlignmentX(FlowLayout.LEADING);
        //  this.setLayout(new BorderLayout());
        //  et.setText("here is hell");
        // JButton b2,b3;
        b2 = new JButton("Next");
        b3 = new JButton("Prev");
        c = new JCheckBox("Match case");
        this.add(b1);
        this.add(l);
        this.add(et);
        this.add(b2);
        this.add(b3);
        this.add(c);
        et.addActionListener(itemHandler);
        b1.addActionListener(itemHandler);
        c.addActionListener(itemHandler);
        b2.addActionListener(itemHandler);
        b3.addActionListener(itemHandler);
      }

    public void single()
      {
        String content = text.textArea.getText();
        suru = content.indexOf(text.s, suru);
        sash = suru + text.s.length();
        if (suru < 0)
          {
            suru = 0;
            suru = content.indexOf(text.s, suru);
            sash = suru + text.s.length();
          }
        try
          {

            text.hilit.addHighlight(suru, sash, text.painter);
            text.textArea.setCaretPosition(sash);
            //  message("'" + s + "' found. Press ESC to end search");
          } catch (BadLocationException e)
          {
            e.printStackTrace();
          }
        suru = sash;
      }

    private class ItemHandler implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
          {
            if (event.getSource() == et)
              {
                // text t=new text();
                text.clear();
                text.s = et.getText();
                suru = 0;
                sash = text.textArea.getText().length();
                if (all == 1)
                  {

                    System.out.println("printing " + text.s);
                    // et.setText("");
                    text.search(text.s);
                  }
                else
                  {
                    single();
                  }
              }
            else if (event.getSource() == b1)
              {
                System.out.println("prin");
                text.s = "";
                et.setText("");
                // text.mode=text.Mode.COMPLETION;
                text.clear();
                text.bc.setVisible(false);
              }
            else if (event.getSource() == c)
              {
                System.out.println("dhukse");
                if (c.isSelected())
                  {
                    all = 1;
                    System.out.println("check box selected");
                  }
                else
                  {
                    System.out.println("Unselected");
                    all = 0;
                    text.clear();
                  }
              }
            else if (event.getSource() == b2)
              {
                text.clear();
                single();
              }
            else if (event.getSource() == b3)
              {
                String content = text.textArea.getText();

                text.clear();
                // single();
                int ns, l = 0, nt = -1;
                ns = sash - text.s.length();
                while (content.indexOf(text.s, l) < ns)
                  {
                    nt = content.indexOf(text.s, l);
                    l = nt + text.s.length();
                  }
                System.out.println("getting pos = " + nt);
                suru = l - text.s.length();
                sash = l;
                if (nt == -1)
                  {
                    sash = text.textArea.getText().length();
                    l = 0;
                    //ns = sash - text.s.length();
                    while (content.indexOf(text.s, l) >= 0)
                      {
                        nt = content.indexOf(text.s, l);
                        l = nt + text.s.length();
                      }
                    suru = nt;
                    sash = nt + text.s.length();
                  }
                try
                  {


                    text.hilit.addHighlight(suru, sash, text.painter);
                    text.textArea.setCaretPosition(l);
                    suru = sash;

            
                  } catch (BadLocationException e)
                  {
                    e.printStackTrace();
                  }
              }
          }
    }
}
