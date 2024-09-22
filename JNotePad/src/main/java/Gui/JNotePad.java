/*
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 *
 * @author Acer
 */
public class JNotePad extends JFrame {

    private JMenuBar mBar;
    private JMenu mFile, mEdit, mFormat, mView, mHelp, mZoom;
    private JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemPrint, itemExit, itemFont, itemReplace, itemFint, itemZoomIn, itemZoomOut, itemRDZoom;
    private JCheckBoxMenuItem itemWrap;
    private JTextArea txtEditor;
    private JToolBar toolBar;
    private JButton btNew, btOpen, btSave;

    public JNotePad(String title) {
        super(title);
        createMenu();
        createGUI();
        processEvent();
        createToolBar();
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createMenu() {
        mBar = new JMenuBar();
        mFile = new JMenu("File");
        mEdit = new JMenu("Edit");
        mFormat = new JMenu("Format");
        mView = new JMenu("View");
        mHelp = new JMenu("Help");

        mBar.add(mFile);
        mBar.add(mEdit);
        mBar.add(mFormat);
        mBar.add(mView);
        mBar.add(mHelp);

        mFile.add(itemNew = new JMenuItem("New"));
        mFile.add(itemOpen = new JMenuItem("Open..."));
        mFile.add(itemSave = new JMenuItem("Save"));
        mFile.add(itemSaveAs = new JMenuItem("Save As..."));
        mFile.addSeparator();
        mFile.add(itemPrint = new JMenuItem("Print..."));
        mFile.addSeparator();
        mFile.add(itemExit = new JMenuItem("Exit"));

        mEdit.add(itemFint = new JMenuItem("Find"));
        mEdit.add(itemReplace = new JMenuItem("Replace"));

        mView.add(mZoom = new JMenu("Zoom"));
        mZoom.add(itemZoomIn = new JMenuItem("Zoom In"));
        mZoom.add(itemZoomOut = new JMenuItem("Zoom Out"));
        mZoom.add(itemRDZoom = new JMenuItem("Restore Default Zoom"));

        mFormat.add(itemWrap = new JCheckBoxMenuItem("Word Wrap", true));
        mFormat.add(itemFont = new JMenuItem("Font..."));

        setJMenuBar(mBar);
    }
    private void processEvent() {
        itemOpen.addActionListener(e -> openFile());
        itemSave.addActionListener(e -> saveFile(false));
        itemSaveAs.addActionListener(e -> saveFile(true));
        itemExit.addActionListener(e -> System.exit(0));
        itemWrap.addActionListener(e -> txtEditor.setLineWrap(itemWrap.isSelected()));
        itemFint.addActionListener(e -> findText());
        itemReplace.addActionListener(e -> replaceText());
        itemZoomIn.addActionListener(e -> zoomIn());
        itemZoomOut.addActionListener(e -> zoomOut());
        itemRDZoom.addActionListener(e -> restoreDefaultZoom());
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                txtEditor.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
            }
        }
    }

    private void saveFile(boolean saveAs) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                txtEditor.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }

    private void findText() {
        String findText = JOptionPane.showInputDialog(this, "Enter text to find:");
        if (findText != null && !findText.isEmpty()) {
            String editorText = txtEditor.getText();
            int index = editorText.indexOf(findText);
            if (index >= 0) {
                txtEditor.setCaretPosition(index + findText.length());
                txtEditor.select(index, index + findText.length());
            } else {
                JOptionPane.showMessageDialog(this, "Text not found!");
            }
        }
    }

    private void replaceText() {
        String findText = JOptionPane.showInputDialog(this, "Enter text to find:");
        if (findText != null && !findText.isEmpty()) {
            String replaceText = JOptionPane.showInputDialog(this, "Enter replacement text:");
            if (replaceText != null) {
                txtEditor.setText(txtEditor.getText().replace(findText, replaceText));
            }
        }
    }

    private void zoomIn() {
        Font currentFont = txtEditor.getFont();
        txtEditor.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), currentFont.getSize() + 2));
    }

    private void zoomOut() {
        Font currentFont = txtEditor.getFont();
        txtEditor.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), currentFont.getSize() - 2));
    }

    private void restoreDefaultZoom() {
        txtEditor.setFont(new Font("Arial", Font.PLAIN, 20));
    }


    private void createGUI() {
        txtEditor = new JTextArea();
        JScrollPane scrollEditor = new JScrollPane(txtEditor);
        add(scrollEditor);
        txtEditor.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.add(btNew = new JButton("New"));
        toolBar.add(btOpen = new JButton("Open"));
        toolBar.add(btSave = new JButton("Save"));

        btNew.setIcon(new ImageIcon(this.getClass().getResource("/images/New.png")));
        btOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/Open.png")));
        btSave.setIcon(new ImageIcon(this.getClass().getResource("/images/Save.png")));

        add(toolBar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        JNotePad notepad = new JNotePad("JNotePad");
        notepad.setVisible(true);
    }
}