/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notepad extends JFrame {

    // Khai báo các thành phần GUI
    private JMenuBar menuBar;
    private JMenu mFile, mEdit, mFormat, mView, mHelp, mZoom;
    private JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemPageSetup, itemPrint, itemExit;
    private JMenuItem itemUndo, itemCut, itemCopy, itemPaste, itemDelete, itemSearchWithBing,
            itemFind, itemFindNext, itemFindPrevious, itemReplace, itemGoTo, itemSelectAll, itemTimeDate;
    private JMenuItem itemZoom;
    private JMenuItem itemZoomIn, itemZoomOut, itemZoomRestore;
    private JMenuItem itemFont;
    private JCheckBoxMenuItem itemWrap, itemStatusBar;

    private JTextArea txtEditor;
    private JToolBar toolBar;
    private JButton btNew, btOpen, btSave;

    private File currentFile; // Lưu trữ tệp hiện tại đang được chỉnh sửa

    public Notepad(String title) {
        super(title);
        createMenu();
        createGui();
        processEvent();
        createToolBar();

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createMenu() {
        //tạo đối tượng thành thực đơn
        menuBar = new JMenuBar();
        //tạo các thực đơn và thêm vào thanh thực đơn
        menuBar.add(mFile = new JMenu("File"));
        menuBar.add(mEdit = new JMenu("Edit"));
        menuBar.add(mFormat = new JMenu("Format"));
        menuBar.add(mView = new JMenu("View"));
        menuBar.add(mHelp = new JMenu("Help"));

        //Tạo các item cho menu File
        mFile.add(itemNew = new JMenuItem("New"));
        mFile.add(itemOpen = new JMenuItem("Open..."));
        mFile.add(itemSave = new JMenuItem("Save"));
        mFile.add(itemSaveAs = new JMenuItem("Save As..."));
        mFile.add(new JSeparator());
        mFile.add(itemPageSetup = new JMenuItem("Page SetUp..."));
        mFile.add(itemPrint = new JMenuItem("Print..."));
        mFile.addSeparator();
        mFile.add(itemExit = new JMenuItem("Exit"));

        //Tạo các item cho menu Format
        mFormat.add(itemWrap = new JCheckBoxMenuItem("Word Wrap", true));
        mFormat.add(itemFont = new JMenuItem("Font"));

        //Tạo phím nóng cho các item
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        //Gắn thanh thực đơn vào cửa sổ
        setJMenuBar(menuBar);

        //Tạo item cho menu Edit
        mEdit.add(itemUndo = new JMenuItem("Undo"));
        mEdit.add(itemCut = new JMenuItem("Cut"));
        mEdit.add(itemCopy = new JMenuItem("Copy"));
        mEdit.add(itemPaste = new JMenuItem("Paste"));
        mEdit.add(itemDelete = new JMenuItem("Delete"));
        mEdit.add(new JSeparator());
        mEdit.add(itemSearchWithBing = new JMenuItem("Search With Bing..."));
        mEdit.add(itemFind = new JMenuItem("Find..."));
        mEdit.add(itemFindNext = new JMenuItem("Find Next"));
        mEdit.add(itemFindPrevious = new JMenuItem("Find Previous"));
        mEdit.add(itemReplace = new JMenuItem("Replace..."));
        mEdit.add(itemGoTo = new JMenuItem("Go To..."));
        mEdit.addSeparator();
        mEdit.add(itemSelectAll = new JMenuItem("Select All"));
        mEdit.add(itemTimeDate = new JMenuItem("Time/Date"));

        //Tạo phím nóng cho item trong edit
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        itemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        itemSearchWithBing.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        itemFindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        itemFindPrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.SHIFT_DOWN_MASK));
        itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
        itemGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
        itemTimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

        //Tạo item cho menu View
        mView.add(mZoom = new JMenu("Zoom"));
        mView.add(itemStatusBar = new JCheckBoxMenuItem("Status Bar"));
        mZoom.add(itemZoomIn = new JMenuItem("Zoom in"));
        mZoom.add(itemZoomOut = new JMenuItem("Zoom out"));
        mZoom.add(itemZoomRestore = new JMenuItem("Restore Default Zoom"));

        //Tạo phím nóng cho các item trong zoom
        itemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK));
        itemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK));
        itemZoomRestore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK));
        
          // Thêm các action listener cho các mục menu
        itemOpen.addActionListener(e -> openFile());
        itemSave.addActionListener(e -> saveFile());
        itemSaveAs.addActionListener(e -> saveFileAs());
        itemExit.addActionListener(e -> exitApplication());
        itemCopy.addActionListener(e -> copyText());
        itemPaste.addActionListener(e -> pasteText());
    }

    private void createGui() {
        txtEditor = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtEditor);
        add(scrollPane);
        txtEditor.setLineWrap(true);
        txtEditor.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    private void processEvent() {
        //xu ly item word wrap
        itemWrap.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (itemWrap.isSelected()) {
                    txtEditor.setLineWrap(true);
                } else {
                    txtEditor.setLineWrap(false);
                }
            }
        });

        //xu ly item open
        itemOpen.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                openFile();
            }
        });

        //xu ly item save
        itemOpen.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                saveFile();
            }

        });
    }

    private void openFile()  {
       JFileChooser dlgFile = new JFileChooser();
        if (dlgFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try{
                //tao luong va lien ket voi tan tin
                FileInputStream fis = new FileInputStream(dlgFile.getSelectedFile());
                byte[] b = new byte[fis.available()];
                //đọc nội dung tập tin
                fis.read(b);
                //Hiển thị vào vùng văn bản
                txtEditor.setText(new String(b));
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi đọc file");
            }
        }
    }

    private void saveFile() {
        JFileChooser dlgFile = new JFileChooser();
        if (dlgFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try{
                //tạo luồng và liên kết với tập tin
                FileOutputStream fos = new FileOutputStream(dlgFile.getSelectedFile());
                //ghi nội dung văn bản ra tạp tin
                fos.write(txtEditor.getText().getBytes());
                //đóng luồng
                fos.close();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Lỗi ghi file");
            }
        }
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
        
            // Thêm action listener cho các nút trên thanh công cụ
        btNew.addActionListener(e -> newFile());
        btOpen.addActionListener(e -> openFile());
        btSave.addActionListener(e -> saveFile());
    }

    private void saveFileAs() {
         JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tệp văn bản", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getParentFile(), selectedFile.getName() + ".txt");
            }
            try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
                byte[] content = txtEditor.getText().getBytes();
                fos.write(content);
                currentFile = selectedFile;
                setTitle(currentFile.getName() + " - JNotepad");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu tệp: " + ex.getMessage(),
                        "Lỗi lưu tệp", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exitApplication() {
          if (hasUnsavedChanges()) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn lưu thay đổi trước khi thoát không?",
                    "Thay đổi chưa được lưu", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        System.exit(0);
    }

    private void copyText() {
           String selectedText = txtEditor.getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            StringSelection stringSelection = new StringSelection(selectedText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    private void pasteText() {
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                String text = (String) clipboard.getData(DataFlavor.stringFlavor);
                txtEditor.replaceSelection(text);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi dán văn bản: " + ex.getMessage(),
                    "Lỗi dán", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void newFile() {
          if (hasUnsavedChanges()) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn lưu thay đổi cho tệp hiện tại không?",
                    "Thay đổi chưa được lưu", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        txtEditor.setText("");
        currentFile = null;
        setTitle("Untitled - JNotepad");
    }

    private boolean hasUnsavedChanges() {
         if (currentFile == null) {
            return !txtEditor.getText().isEmpty();
        }
        try (FileInputStream fis = new FileInputStream(currentFile)) {
            byte[] fileContent = new byte[(int) currentFile.length()];
            fis.read(fileContent);
            String fileText = new String(fileContent);
            return !fileText.equals(txtEditor.getText());
        } catch (IOException ex) {
            return true; // Giả định có thay đổi chưa được lưu nếu không thể đọc tệp
        }
    }
    public static void main(String[] args) {
         Notepad app= new Notepad("Demo Notepad");
        app.setVisible(true);
    }
    
}