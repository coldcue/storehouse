package com.storehouse.view;

import com.storehouse.model.ItemStore;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import sun.awt.OrientableFlowLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/29/13
 * Time: 9:04 PM
 */
public class MainFrame {
    private ItemStore itemStore;

    private JLabel statusLabel;
    private JTable table;
    private JButton deleteButton;
    private JFrame frame;

    public MainFrame(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public void init() {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException ignored) {
        }
        frame = new JFrame("Storehouse");
        frame.setLayout(new BorderLayout());
        //frame.setMinimumSize(new Dimension(800, 600));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            frame.setIconImage(ImageIO.read(this.getClass().getResourceAsStream("/icon.png")));
        } catch (IOException ignored) {
        }

        statusLabel = new JLabel();
        statusLabel.setText("Initializing...");

        /**
         * Add menubar
         */
        frame.setJMenuBar(getMenuBar());

        /**
         * Init start panel
         */
        initStartPanel(frame);

        /**
         * Init Table
         */
        initTable(frame);

        /**
         * End Panel
         */
        frame.add(statusLabel, BorderLayout.PAGE_END);

        /**
         * Set to visible
         */
        frame.setVisible(true);

        statusLabel.setText("Loaded!");
    }

    private void initTable(JFrame frame) {
        table = new JTable(new ItemData((itemStore)));
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRowCount = table.getSelectedRowCount();
                statusLabel.setText(selectedRowCount + " rows selected");
                if (selectedRowCount >= 1) deleteButton.setEnabled(true);
            }
        });
    }

    private JMenuBar getMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        /**
         * File menu
         */
        JMenu fileMenu = new JMenu("File");


        /**
         * New table
         */
        JMenuItem newFile = new JMenuItem("New");
        fileMenu.add(newFile);

        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure? All unsaved stuff will be lost!", "New store", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option == 0) {
                    itemStore.purge();
                }
            }
        });

        /**
         * Open database file
         */
        JMenuItem loadFromFile = new JMenuItem("Open...");
        fileMenu.add(loadFromFile);

        loadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser jFileChooser = new JFileChooser(new File(""));
                jFileChooser.showOpenDialog(frame);
                jFileChooser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        File file = jFileChooser.getSelectedFile();
                        //This is where a real application would open the file.
                        statusLabel.setText("Opening: " + file.getName());
                    }
                });
            }
        });

        JMenuItem save = new JMenuItem("Save");
        fileMenu.add(save);
        JMenuItem saveAs = new JMenuItem("Save as");
        fileMenu.add(saveAs);

        menuBar.add(fileMenu);


        /**
         * Help menu
         */
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");


        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * About dialog
                 */
                JDialog aboutDialog = new JDialog(frame, "About", true);
                aboutDialog.setSize(400, 210);
                aboutDialog.setLocationRelativeTo(null);

                GridLayout layout = new GridLayout();
                layout.setRows(1);
                layout.setColumns(2);
                aboutDialog.setLayout(layout);

                ImagePanel imagePanel = new ImagePanel(this.getClass().getResourceAsStream("/icon.png"), 20, 20, 128, 128);
                aboutDialog.add(imagePanel);

                JPanel infoPanel = new JPanel();
                infoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.add(new JLabel("<html><b><font size=+2>Storehouse</font></b></html>"));
                infoPanel.add(new JLabel("<html><b><font size=+1>Author:</font></b></html>"));
                infoPanel.add(new JLabel("<html><b>Széll András</b></html>"));
                infoPanel.add(new JLabel("<html><b>DP1FGW</b></html>"));
                aboutDialog.add(infoPanel);

                aboutDialog.setVisible(true);

                imagePanel.paintComponent(aboutDialog.getGraphics());
            }
        });
        helpMenu.add(about);

        menuBar.add(helpMenu);


        return menuBar;
    }

    private void initStartPanel(JFrame frame) {
        /**
         * Start Panel
         */
        OrientableFlowLayout layout = new OrientableFlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        JPanel startPanel = new JPanel(layout);
        frame.add(startPanel, BorderLayout.PAGE_START);

        JButton addItemButton = new JButton("New item");
        startPanel.add(addItemButton);

        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        startPanel.add(deleteButton);
    }
}
