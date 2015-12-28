/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad_03;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

/**
 *
 * @author taf
 */
public class text extends JFrame
        implements DocumentListener
{

    public static Object[] options =
      {
        "Save",
        "Don't Save",
        "Cancel"
      };
    public static boolean state = false;
    public static String s = "";
    public static String pop_option[] =
      {
        "Cut", "Copy", "Paste", "Change background"
      };
    //pop block
    public static JMenuItem[] r_op;
    public static String r_name[] =
      {
        "Cut", "Copy", "Paste", "Change Background Color", "Select All"
      };
    public static JPopupMenu r_menu;
    //end
    public static String completion, content;
    public static int zindex = 8, sindex = 0, findex = 73, tem, line = 0, flag = 0;
    public static int p, q, r;
    public static Font ttf;
    public static JMenuItem stat,New, Open, Save, Exit, Copy, Cut, Paste, Find, Replace, font, about;
    public static JCheckBox wrap;
    public static JScrollPane scrollPane;
    public static Scanner input;
    public static Formatter output;
    public static int save = 0;
    public static Scanner scan;
    public static JScrollPane jScrollPane1;
    public static JTextArea textArea;
    final static Color HILIT_COLOR = Color.yellow;
 
    public static Highlighter hilit;
    public static Highlighter.HighlightPainter painter;
    static JFrame f = new JFrame("Font And Format");
    static String[] fntname = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    static String[] fnt =
      {
        "Font", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"
      };
    static int[] fnts =
      {
        10, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30
      };
    static int[] styles =
      {
        Font.PLAIN, Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC
      };
    static String[] style_name =
      {
        "Styles", "Plain", "Bold", "Italic", "Bold+Italic"
      };
    public static JComboBox style = new JComboBox(style_name);
    public static JComboBox com = new JComboBox(fnt);
    public static JComboBox fname = new JComboBox(fntname);
    public static JButton done = new JButton("Done");
    public static JButton cancel = new JButton("Cancel");
    public static JTextArea demofont = new JTextArea(150, 30);
    public static boolean b = false;
    public static bottom_menu bc;

    public text()
      {
        super("Notepad");
        //font block
        demofont.setWrapStyleWord(true);
        demofont.setLineWrap(true);
        demofont.setBackground(Color.WHITE);
        f.setSize(400, 400);
        f.setLocation(400, 200);
        f.setBackground(Color.WHITE);
        f.setLayout(new FlowLayout());
        f.setVisible(false);
        f.setResizable(false);
        demofont.setMargin(new Insets(10, 15, 10, 10));
        //   com.set
        f.add(com);
        f.add(style);
        f.add(fname);
        f.add(new JScrollPane(demofont));
        f.add(done);
        f.add(cancel);

        f.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //end
        initComponents();
        ItemHandler itemHandler = new ItemHandler();
        //pop
        ttf = new Font("Courier New", 0, 24);
        demofont.setText("A Quick Brown Fox Jumps Over A Lazy Dog");
        demofont.setEditable(false);
        demofont.setLineWrap(true);
        textArea.setFont(ttf);
        r_menu = new JPopupMenu();
        r_op = new JMenuItem[r_name.length];
        for (int i = 0; i < r_name.length; ++i)
          {
            r_op[i] = new JMenuItem(r_name[i]);
            r_menu.add(r_op[i]);
            r_op[i].addActionListener(itemHandler);
          }
        //block end
        New.addActionListener((ActionListener) itemHandler);
        Open.addActionListener(itemHandler);
        Save.addActionListener(itemHandler);
        Exit.addActionListener(itemHandler);
        Copy.addActionListener(itemHandler);
        Cut.addActionListener(itemHandler);
        Paste.addActionListener(itemHandler);
        Find.addActionListener(itemHandler);
        Replace.addActionListener(itemHandler);
        wrap.addActionListener(itemHandler);
        font.addActionListener(itemHandler);
        cancel.addActionListener(itemHandler);
        done.addActionListener(itemHandler);
        style.addActionListener(itemHandler);
        fname.addActionListener(itemHandler);
        com.addActionListener(itemHandler);
        stat.addActionListener(itemHandler);
        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
        textArea.setHighlighter(hilit);
        textArea.getDocument().addDocumentListener(this);
        
     
        textArea.addMouseListener(
                new MouseAdapter()
                {

                    @Override
                    public void mousePressed(MouseEvent e)
                      {
                        //System.out.println("pressed");
                        check(e);
                      }

                    @Override
                    public void mouseReleased(MouseEvent e)
                      {
                        //System.out.println("pressed");
                        check(e);
                      }

                    private void check(MouseEvent e)
                      {
                        if (e.isPopupTrigger())
                          {
                            r_menu.show(e.getComponent(), e.getX(), e.getY());
                          }
                      }
                });

      }

    private void initComponents()
      {



     
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu format = new JMenu("Format");
        JMenu viewMenu = new JMenu("View");
        JMenu helpMenu = new JMenu("Help");
        
        New = new JMenuItem("New");
        New.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        Open = new JMenuItem("Open");
        Open.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        Save = new JMenuItem("Save");
        Save.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        Exit = new JMenuItem("Exit");
        Exit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
       fileMenu.add(Open);
        fileMenu.add(New);
        
        fileMenu.add(Save);
        fileMenu.add(Exit);

        Copy = new JMenuItem("Copy");
        Copy.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        Cut = new JMenuItem("Cut");
        Cut.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        Paste = new JMenuItem("Paste");
        Paste.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        Find = new JMenuItem("Find");
        Find.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        Replace = new JMenuItem("Replace");
        Replace.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        editMenu.add(Cut);
        editMenu.add(Copy);
        editMenu.add(Paste);
        editMenu.add(Find);
        editMenu.add(Replace);
        wrap = new JCheckBox("Word Wrap");
        font = new JMenuItem("    Font...");

        font.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        stat = new JMenuItem("Change color");
        format.add(wrap);
        format.add(font);
        helpMenu.add(about);
        viewMenu.add(stat);
        JMenuBar bar = new JMenuBar(); 
        bar.add(fileMenu);
        bar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        

        bar.add(editMenu);
       
        bar.add(format);
        bar.add(viewMenu);
       

        bar.add(helpMenu);

        setJMenuBar(bar);

        textArea = new JTextArea();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("texteditor");

        textArea.setColumns(20);
        textArea.setLineWrap(false);
        textArea.setRows(5);
        textArea.setWrapStyleWord(false);
        textArea.setEditable(true);
        jScrollPane1 = new JScrollPane(textArea);
        this.setLayout(new BorderLayout());
        this.add(jScrollPane1, BorderLayout.CENTER);
        bc = new bottom_menu();
        this.add(bc, BorderLayout.SOUTH);
        bc.setVisible(false);
      }

    public static void search(String s)
      {
        hilit.removeAllHighlights();

       
        if (s.length() <= 0)
          {
            
            return;
          }

        content = textArea.getText();
        int index = content.indexOf(s, 0);

        while (index >= 0)
          {
            int end = index + s.length();
           
            try
              {

                hilit.addHighlight(index, end, painter);
                textArea.setCaretPosition(end);
                
              } catch (BadLocationException e)
              {
                e.printStackTrace();
              }
            index = content.indexOf(s, end);
          }

      }

    public void insertUpdate(DocumentEvent ev)
      {
       
       
      }

    public void removeUpdate(DocumentEvent ev)
      {
        search(s);
      }

    public void changedUpdate(DocumentEvent ev)
      {
      }

    private class ItemHandler implements ActionListener
    {

        public void actionPerformed(ActionEvent event)
          {
            int n = 3;
            if (event.getSource() == New)
              {
                if (save == 0)
                  {
                    n = JOptionPane.showOptionDialog(null,
                            "Do you like " + "to save the changes?",
                            "Notepad",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[2]);

                    System.out.println(" N = " + n);
                    if (n == 0)
                      {
                        JFileChooser filechooser = new JFileChooser();
                        int r = filechooser.showSaveDialog(text.this);
                        if (r != JFileChooser.CANCEL_OPTION)
                          {
                            saveFile(filechooser.getSelectedFile());

                          }
                      }
                  }
                if (n != 2)
                  {
                    save = 0;
                    textArea.setText("");
                    System.out.println("Creating new file");
                  }
              }
            else if (event.getSource() == Open)
              {
                JFileChooser filechooser = new JFileChooser();
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int r = filechooser.showOpenDialog(text.this);
                if (r != JFileChooser.CANCEL_OPTION)
                  {
                    openFile(filechooser.getSelectedFile());
                  }
                System.out.println("opening file");
              }
            else if (event.getSource() == Save)
              {
                JFileChooser filechooser = new JFileChooser();
                int r = filechooser.showSaveDialog(text.this);
                if (r != JFileChooser.CANCEL_OPTION)
                  {
                    saveFile(filechooser.getSelectedFile());
                  }
              
                System.out.println("Saving File");
              }
            else if (event.getSource() == Exit)
              {

                n = 3;
                if (text.save == 0)
                  {
                    n = JOptionPane.showOptionDialog(null,
                            "Do you like " + "to save the changes?",
                            "Notepad",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            text.options,
                            text.options[2]);

                    System.out.println(" N = " + n);
                    if (n == 0)
                      {
                        JFileChooser filechooser = new JFileChooser();
                        int r = filechooser.showSaveDialog(null);
                        if (r != JFileChooser.CANCEL_OPTION)
                          {
                            text.saveFile(filechooser.getSelectedFile());

                          }
                      }
                  }
                System.exit(0);
              }
            else if (event.getSource() == Find)
              {
                bc.setVisible(true);
                search(s);
              }
            else if (event.getSource() == Replace)
              {

                Replace();
              }
            else if (event.getSource() == r_op[0] || event.getSource() == Cut)
              {

                textArea.cut();

              }
            else if (event.getSource() == r_op[1] || event.getSource() == Copy)
              {

                textArea.copy();

              }
            else if (event.getSource() == r_op[2] || event.getSource() == Paste)
              {
                textArea.paste();

              }
            else if (event.getSource() == r_op[3])
              {
                Color color = null;
                textArea.setBackground(JColorChooser.showDialog(text.this, "Choose Background Color", color));
                System.out.println("Changing background");
              }
            else if (event.getSource() == r_op[4])
              {
                textArea.selectAll();
              }
            else if (event.getSource() == font)
              {
                f.setVisible(true);
              }
            else if (event.getSource() == done)
              {
                System.out.println("selecing font");
                textArea.setFont(new Font(fntname[findex], styles[sindex], fnts[zindex]));
                f.setVisible(false);
              }
            else if (event.getSource() == cancel)
              {
                f.setVisible(false);
              }
            else if (event.getSource() == fname)
              {

                demofont.setText("A Brown Fox Jumps Over A Lazy Dog");
                findex = fname.getSelectedIndex();
                demofont.setFont(new Font(fntname[findex], styles[sindex], fnts[zindex]));
                demofont.setText("A Quick Brown Fox Jumps Over A Lazy Dog");
                System.out.println("hafiz " + findex);

              }
            else if (event.getSource() == style)
              {
                int x = 0;
                x = style.getSelectedIndex();
                if (x != 0)
                  {

                    sindex = x;
                    demofont.setFont(new Font(fntname[findex], styles[sindex], fnts[zindex]));
                    demofont.setText("A Quick Brown Fox Jumps Over A Lazy Dog");
                    System.out.println("Style " + x);
                  }

              }
            else if (event.getSource() == com)
              {
                int y = 0;
                y = com.getSelectedIndex();
                if (y != 0)
                  {

                    zindex = y;
                    demofont.setFont(new Font(fntname[findex], styles[sindex], fnts[zindex]));
                    demofont.setText("A Brown Fox Jumps Over A Lazy Dog");
                  }
                System.out.println("Size " + y + " " + zindex);
              }
            else if (event.getSource() == wrap)
              {
                if (wrap.isSelected())
                  {
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                  }
                else
                  {
                    textArea.setLineWrap(false);
                  }
              }
            else if (event.getSource() == stat)
              {
                Color temcolor=null;
            
                   temcolor=JColorChooser.showDialog(text.this, "Choose A Color", temcolor);
                   if(temcolor==null)
                       temcolor=Color.black;
                   textArea.setForeground(temcolor);
                
              }
         
          }
    }

    
    
    public void Replace()
      {
        String newWord = "";
        String old = "";
        String rd = "";
        old = (String) JOptionPane.showInputDialog(
                null,
                "Find Word",
                "Replace Dialogue",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");
        if (old != null && old.length() > 0)
          {
            rd = (String) JOptionPane.showInputDialog(
                    null,
                    "New Word",
                    "Replace Dialogue",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

          }
        int begin = 0;
        System.out.println("word to replace is " + rd + "   " + old);
        String tem = textArea.getText();
        System.out.println(tem);
        tem = tem.replaceAll(old, rd);
        System.out.println(tem);
        textArea.setText(tem);
      }

    public static void clear()
      {
        hilit.removeAllHighlights();
        tem = textArea.getText().length();
        textArea.setCaretPosition(tem);
      }

    void openFile(File newFile)
      {
        try
          {
            input = new Scanner(new File(newFile.getPath()));

          } 
        catch (FileNotFoundException fileNotFoundException)
          {
            System.err.println("Error opening file.");
            System.exit(1);
          }
        while (input.hasNext())
          {

            String word = input.nextLine();
            word += '\n';
            textArea.append(word);
         

          }
      }

    public static void saveFile(File savedFile)
      {
        try
          {
            output = new Formatter(savedFile.getPath());
          } 
        catch (IOException ioException)
          {
            System.err.println("Error opening file.");
          }
        String data = textArea.getText();
        output.format("%s", data);
        output.close();
        save = 1;
      }
}

