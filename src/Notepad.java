import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {
    private TextArea textArea = new TextArea("", 0,0, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private MenuBar menuBar = new MenuBar();
    private Menu file = new Menu();
    private MenuItem openFile = new MenuItem();
    private MenuItem saveFile = new MenuItem();
    private MenuItem close = new MenuItem();

    private Menu compile = new Menu();
    private MenuItem compileProgram = new MenuItem();
    private MenuItem debugProgram = new MenuItem();

    public Notepad() {
        this.setSize(500, 300);
        this.setTitle("JNotepad 0.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.textArea.setFont(new Font("Times New Roman", Font.BOLD, 12));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(textArea);
        this.setMenuBar(this.menuBar);
        this.menuBar.add(this.file);
        this.file.setLabel("File");
        this.openFile.setLabel("Open");
        this.openFile.addActionListener(this);
        this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
        this.file.add(this.openFile);
        this.saveFile.setLabel("Save");
        this.saveFile.addActionListener(this);
        this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
        this.file.add(this.saveFile);
        this.close.setLabel("Close");
        this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
        this.close.addActionListener(this);
        this.file.add(this.close);

        this.menuBar.add(this.compile);
        this.compile.setLabel("Compiler");
        this.compile.add(this.compileProgram);
        this.compileProgram.setLabel("Compile");
        this.compileProgram.addActionListener(this);
        this.compileProgram.setShortcut(new MenuShortcut(KeyEvent.VK_F9, false));
        this.compile.add(this.debugProgram);
        this.debugProgram.setLabel("Run with debugger");
        this.debugProgram.addActionListener(this);
        this.debugProgram.setShortcut(new MenuShortcut(KeyEvent.VK_F10, false));
    }

    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == this.close) {
            this.dispose();
        }
        else if (e.getSource() == this.openFile) {
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                this.textArea.setText("");
                try {
                    Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                    while (scan.hasNext()) {
                        this.textArea.append(scan.nextLine() + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        else if (e.getSource() == this.saveFile) {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
                    out.write(this.textArea.getText());
                    out.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        else if (e.getSource() == this.compileProgram) {
            JFileChooser compile = new JFileChooser();
            int option = compile.showSaveDialog(this);
            if(option == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(compile.getSelectedFile().getPath()));
                    out.write(this.textArea.getText());
                    out.close();
                    System.out.println(compile.getSelectedFile().getCanonicalPath());
                    Runtime.getRuntime().exec("gcc -o" + compile.getSelectedFile().getAbsolutePath() + ".elf" + compile.getSelectedFile().getPath());
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        }
    }
    public static void main(String args[]) {
        Notepad app = new Notepad();
        app.setVisible(true);
    }
}