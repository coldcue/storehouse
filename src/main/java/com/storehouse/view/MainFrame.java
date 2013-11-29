package com.storehouse.view;

import sun.awt.OrientableFlowLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 11/29/13
 * Time: 9:04 PM
 */
public class MainFrame {

    private final JLabel statusLabel;
    private final JPanel southPanel;

    public MainFrame() {
        JFrame frame = new JFrame("Storehouse");
        frame.setLayout(new BorderLayout());
        //frame.setMinimumSize(new Dimension(800, 600));
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            frame.setIconImage(ImageIO.read(Main.class.getResourceAsStream("/icon.png")));
        } catch (IOException ignored) {
        }

        statusLabel = new JLabel();
        statusLabel.setText("Initializing...");

        /**
         * South Panel
         */
        OrientableFlowLayout layout = new OrientableFlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        southPanel = new JPanel(layout);
        frame.add(southPanel, BorderLayout.SOUTH);
        southPanel.add(statusLabel);
    }
}
