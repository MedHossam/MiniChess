/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Class used to set the background of frame contentPanel
package tn.rnu.isi.glsi.minichess.core;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Med
 */
public class Square extends JPanel {

    private Image image;

    /**
     * Default constructor used to set the image for the background for the
     * instance
     */
    public Square(Image img) {
        image = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //draws image to background to scale of frame
        g.drawImage(image, 0, 0, null);
    }
}
