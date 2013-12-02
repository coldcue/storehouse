package com.storehouse.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew
 * Date: 12/2/13
 * Time: 6:22 PM
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;
    private int x, y;

    public ImagePanel(InputStream inputStream, int x, int y) {
        try {
            image = ImageIO.read(inputStream);
            this.x = x;
            this.y = y;
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, null);
    }

}