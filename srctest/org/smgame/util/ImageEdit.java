/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author packyuser
 */
public class ImageEdit {

    public static ImageIcon grayScaleImage(ImageIcon colorImage) {
        int width = 32;
        int height = 49;
        BufferedImage grayScaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayScaleImage.getGraphics();
        g.drawImage(colorImage.getImage(), 0, 0, null);
        g.dispose();
        return new ImageIcon(grayScaleImage);
    }

    //Resizes an image using a Graphics2D object backed by a BufferedImage.
    public static ImageIcon scaledImage(ImageIcon image) {
        int width = 32;
        int height = 49;
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image.getImage(), 0, 0, width, height, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
}
