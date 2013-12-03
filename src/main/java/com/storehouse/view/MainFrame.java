package com.storehouse.view;

import com.storehouse.model.FileManager;
import com.storehouse.model.Item;
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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/29/13
 * Time: 9:04 PM
 */
public class MainFrame {
    private ItemStore itemStore;
    private FileManager fileManager;

    private JLabel statusLabel;
    private JTable table;
    private JButton deleteButton;
    private JFrame frame;
    private ItemData itemData;

    public MainFrame(ItemStore itemStore, FileManager fileManager) {
        this.itemStore = itemStore;
        this.fileManager = fileManager;
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
        initStartPanel();

        /**
         * Init Table
         */
        initTable();

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

    private void initTable() {
        itemData = new ItemData(itemStore);
        table = new JTable(itemData);
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
                    itemData.fireTableDataChanged();
                    fileManager.close();
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
                int ret = jFileChooser.showOpenDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                    statusLabel.setText("Opening: " + file.getName());
                    try {
                        fileManager.open(file);
                        itemData.fireTableDataChanged();
                    } catch (IOException | ClassNotFoundException e1) {
                        JOptionPane.showMessageDialog(frame, "Can't open the file! Maybe it's not a real database...", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    statusLabel.setText("Database loaded from: " + file.getName());
                }
            }
        });

        /**
         * Save or save as
         */
        JMenuItem save = new JMenuItem("Save");
        fileMenu.add(save);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileManager.isOpened()) {
                    try {
                        fileManager.save();
                        statusLabel.setText("Everything is saved!");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else
                    saveAs();
            }
        });

        JMenuItem saveAs = new JMenuItem("Save as");
        fileMenu.add(saveAs);

        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });

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

    private void initStartPanel() {
        /**
         * Start Panel
         */
        OrientableFlowLayout layout = new OrientableFlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        JPanel startPanel = new JPanel(layout);
        frame.add(startPanel, BorderLayout.PAGE_START);

        JButton addItemButton = new JButton("New item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newItemDialog();
            }
        });
        startPanel.add(addItemButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row : table.getSelectedRows()) {
                    itemStore.removeItem(itemStore.getItem(row));
                }
                statusLabel.setText(table.getSelectedRowCount() + " items deleted");
                itemData.fireTableDataChanged();
            }
        });
        deleteButton.setEnabled(false);
        startPanel.add(deleteButton);
    }


    private void saveAs() {
        final JFileChooser jFileChooser = new JFileChooser(new File("database.db"));
        int ret = jFileChooser.showSaveDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            statusLabel.setText("Saving to: " + file.getName());
            try {
                fileManager.save(file);
            } catch (IOException e) {
                statusLabel.setText("Save failed to: " + file.getName());
            }
            statusLabel.setText("Database saved to: " + file.getName());
        }
    }

    private void newItemDialog() {
        final JDialog dialog = new JDialog(frame, "New item", true);
        dialog.setSize(300, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        /**
         * Fields panel
         */
        JPanel fieldsPanel = new JPanel();
        BoxLayout layout = new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS);
        fieldsPanel.setLayout(layout);

        final Map<Item.Field, JTextField> textFieldMap = new HashMap<>();

        for (Item.Field field : Item.Field.values()) {
            if (field == Item.Field.ID) continue;
            fieldsPanel.add(new JLabel(field.getName()));
            JTextField textField = new JTextField();
            textFieldMap.put(field, textField);
            fieldsPanel.add(textField);
        }

        dialog.add(fieldsPanel, BorderLayout.CENTER);

        JPanel endPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        dialog.add(endPanel, BorderLayout.PAGE_END);
        /**
         * Add button
         */
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Item item = new Item();

                    for (Map.Entry<Item.Field, JTextField> entry : textFieldMap.entrySet()) {
                        Item.Field field = entry.getKey();
                        String text = entry.getValue().getText();
                        Method method = Item.class.getMethod(field.getSetMethodName(), field.getClazz());

                        if (field.getClazz() == int.class) {
                            method.invoke(item, Integer.parseInt(text));
                        } else if (field.getClazz() == long.class) {
                            method.invoke(item, Long.parseLong(text));
                        } else method.invoke(item, text);
                    }

                    itemStore.addItem(item);
                    itemData.fireTableDataChanged();
                    dialog.setVisible(false);
                } catch (Exception ignored) {
                    JOptionPane.showMessageDialog(dialog, "Something is missing or mistyped!", "Wrong input(s)", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        endPanel.add(addButton);

        /**
         * Cancel button
         */
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        endPanel.add(cancelButton);

        dialog.setVisible(true);
    }
}
