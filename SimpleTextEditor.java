import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SimpleTextEditor extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private int fontSize = 12; // Default font size
    private String fontFamily = "Arial"; // Default font family

    public SimpleTextEditor() {
        setTitle("Simple Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setFont(new Font(fontFamily, Font.PLAIN, fontSize)); // Set initial font
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");

        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFileAs();
            }
        });

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);

        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        copyMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });

        cutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });

        pasteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });

        editMenu.add(copyMenuItem);
        editMenu.add(cutMenuItem);
        editMenu.add(pasteMenuItem);

        menuBar.add(editMenu);

        JMenu fontMenu = new JMenu("Font");
        JMenuItem selectFontMenuItem = new JMenuItem("Select Font");

        selectFontMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectFont();
            }
        });

        fontMenu.add(selectFontMenuItem);
        menuBar.add(fontMenu);

        JMenu viewMenu = new JMenu("View");
        JMenuItem zoomInMenuItem = new JMenuItem("Zoom In");
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom Out");

        zoomInMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomIn();
            }
        });

        zoomOutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomOut();
            }
        });

        viewMenu.add(zoomInMenuItem);
        viewMenu.add(zoomOutMenuItem);

        menuBar.add(viewMenu);

        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();

        setVisible(true);
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                textArea.read(reader, null);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                textArea.write(writer);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFileAs() {
        fileChooser.setSelectedFile(null);
        saveFile();
    }

    private void zoomIn() {
        fontSize += 2; // Increase the font size by 2
        textArea.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
    }

    private void zoomOut() {
        if (fontSize > 2) { // Ensure the font size is not too small
            fontSize -= 2; // Decrease the font size by 2
            textArea.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
        }
    }

    private void selectFont() {
        // Create a font selection dialog
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String selectedFontName = (String) JOptionPane.showInputDialog(
                this,
                "Select a Font:",
                "Font Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                fontNames,
                fontFamily
        );

        if (selectedFontName != null) {
            fontFamily = selectedFontName;
            textArea.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleTextEditor();
            }
        });
    }
}
