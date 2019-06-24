package notepad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notepad extends JFrame{
        private JFrame frame;
        private JToolBar tb;
        private JButton newBut, openBut, saveBut, clearBut, copyBut, statsBut, exitBut;
        private JMenuBar menuBar;
        private JMenu file, edit;
        private JMenuItem newFile, openFile, saveFile, clearFile, copyFile, stats, exit;
        private JTextArea textArea;
        private JTextField textField;
        private JFileChooser fileChooser;
        private FileNameExtensionFilter filter;
        private JScrollPane scroll; //Για την δημιουργία scrollbar
        
        public Notepad() {
            frame = new JFrame("Notepad Project");
            tb = new JToolBar();
            menuBar = new JMenuBar();
            file = new JMenu("File");
            edit = new JMenu("Edit");
            
            newBut = new JButton("New");
            openBut = new JButton("Open");
            saveBut = new JButton("Save");
            clearBut = new JButton("Clear");
            copyBut = new JButton("Copy(Save As)");
            statsBut = new JButton("Stats");
            exitBut = new JButton("Exit");
            
            newFile = new JMenuItem("New File");
            openFile = new JMenuItem("Open");
            saveFile = new JMenuItem("Save");
            clearFile = new JMenuItem("Clear");
            copyFile = new JMenuItem("Copy(Save As)");
            stats = new JMenuItem("Statistics");
            exit = new JMenuItem("Exit");

            textArea = new JTextArea(5, 30);
            textField = new JTextField();
            fileChooser = new JFileChooser();
            filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); //Δεχεται μόνο txt files.
            fileChooser.setFileFilter(filter); //Θέτω το φιλτρο για τα αρχεια 
            scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //Scrollbar 
            
            frame.setSize(750, 550);
            frame.setVisible(true);
            frame.setResizable(true);
            frame.setDefaultCloseOperation(Notepad.EXIT_ON_CLOSE);
            
            tb.setSize(250, 400);
            tb.setVisible(true);
            tb.setFloatable(false);
            
            frame.setJMenuBar(menuBar);
            textField.setEditable(false); //Lock-αρει την γραμμή διεύθυνσης αρχείου
            textArea.setLineWrap(true);
            
            menuBar.add(file);
            menuBar.add(edit);

            tb.add(newBut);
            tb.add(openBut);
            tb.add(clearBut);
            tb.add(saveBut);
            tb.add(copyBut);
            tb.add(statsBut);
            tb.add(exitBut);

            file.add(newFile);
            file.add(openFile);
            file.add(exit);
            edit.add(clearFile);
            edit.add(saveFile);
            edit.add(copyFile);
            edit.add(stats);
            
            frame.add(scroll);
            frame.add(textField, BorderLayout.SOUTH);
            frame.add(tb, BorderLayout.NORTH);
            
            NewListener newL = new NewListener();
            newFile.addActionListener(newL);
            newBut.addActionListener(newL);
            
            OpenListener openL = new OpenListener();
            openFile.addActionListener(openL);
            openBut.addActionListener(openL);
            
            ExitListener exitL = new ExitListener();
            exit.addActionListener(exitL);
            exitBut.addActionListener(exitL);            
            
            ClearListener clearL = new ClearListener();
            clearFile.addActionListener(clearL);
            clearBut.addActionListener(clearL);

            SaveListener saveL = new SaveListener();
            saveFile.addActionListener(saveL);
            saveBut.addActionListener(saveL);

            CopyListener copyL = new CopyListener();
            copyFile.addActionListener(copyL);
            copyBut.addActionListener(copyL);
            
            StatsListener statsL = new StatsListener();
            stats.addActionListener(statsL);
            statsBut.addActionListener(statsL);
            
        }
            class NewListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(frame)) {
                    File file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    try {
                        textField.setText(path);
                        textArea.setText("");  
                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(textArea.getText());
                        bw.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage()); 
                    }
                }
            }
        }
        
        
            class OpenListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(frame)) {
                    File f = fileChooser.getSelectedFile();
                    String path = f.getAbsolutePath();
                    textField.setText(path);
                    
                    Scanner scan = null;
                    
                    try { 
                        String s1 = "", s2 = ""; 
                        FileReader fr = new FileReader(f); 
                        BufferedReader br = new BufferedReader(fr); 
                        s2 = br.readLine(); 
                        
                        while ((s1 = br.readLine()) != null) { 
                            s2 = s2 + "\n" + s1; 
                        } 
                        textArea.setText(s2); 
                    
                    } 
                    catch (IOException ex) { 
                        JOptionPane.showMessageDialog(frame, ex.getMessage()); 
                    } 
             
                }    
           }  
        }
    
        
            class ExitListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Θες σίγουρα να κλείσεις το πρόγραμμα;", "Exit", JOptionPane.YES_NO_OPTION);
                
                if(confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }  
            }
        
        
            class ClearListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Θες σίγουρα να κάθαρίσεις το αρχείο;", "Clear", JOptionPane.YES_NO_OPTION);
               
                if(confirm == JOptionPane.YES_OPTION) {
                    textArea.setText("");
                }

            }
            
            }
        
        
            class SaveListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fileChooser.getSelectedFile();
                    try {
                      FileWriter fw = new FileWriter(file.getAbsoluteFile());
                      BufferedWriter bw = new BufferedWriter(fw);
                      bw.write(textArea.getText());
                      bw.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage()); 
                    }
            }
            }
            
        
            class CopyListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(frame)) {
                    File file = fileChooser.getSelectedFile();
                    try {
                      FileWriter wr = new FileWriter(file, false); 
                      BufferedWriter bw = new BufferedWriter(wr); 
                      bw.write(textArea.getText()); 
                      bw.flush(); 
                      bw.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage()); 
                    }
                }
            }
            }
        
        
            class StatsListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
               
                String s = textArea.getText();
                
                String[] a = s.split("\\s+|\n");
                String[] b = s.split("\\s+");
                
                int wordCount = a.length;
                int charCount = s.length();
                int paragraphs = textArea.getLineCount();   
                    
                JOptionPane.showMessageDialog(null,"\nWord(s): "+wordCount + 
                        "\nCharacter(s): "+charCount + 
                        "\nCharacter(s) without space(s): "+(charCount-(b.length-1)) + //ΔΕΝ ΔΟΥΛΕΥΕΙ ΣΩΣΤΑ
                        "\nParagraphs: "+paragraphs + 
                        "\nSize in KB: "+(int)charCount/1024 , "Stats", JOptionPane.INFORMATION_MESSAGE);
            }
            }
 
        
       public static void main(String args[])  {
           Notepad note = new Notepad();
       }

}
