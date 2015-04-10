/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.rnu.isi.glsi.minichess.core;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import tn.rnu.isi.glsi.minichess.ui.MainWindow;

/**
 *
 * @author Med
 */
public class Piece extends JLabel {

    // Empty Piece
    public static final String EPi = "";

    // White King
    public static final String WKi = "WKi";
    // White Queen
    public static final String WQu = "WQu";
    // White Tower
    public static final String WTo = "WTo";
    // White Bishop
    public static final String WBi = "WBi";
    // White Knight
    public static final String WKn = "WKn";
    // White Pawn
    public static final String WPa = "WPa";

    // Black King
    public static final String BKi = "BKi";
    // Black Queen
    public static final String BQu = "BQu";
    // Black Tower
    public static final String BTo = "BTo";
    // Black Bishop
    public static final String BBi = "BBi";
    // Black Knight
    public static final String BKn = "BKn";
    // Black Pawn
    public static final String BPa = "BPa";

    // Initial Positions
    public static final String[] INITIALPOSITIONS = {
        BKn, BBi, BQu, BKi, BTo,
        BPa, BPa, BPa, BPa, BPa,
        EPi, EPi, EPi, EPi, EPi,
        EPi, EPi, EPi, EPi, EPi,
        EPi, EPi, EPi, EPi, EPi,
        EPi, EPi, EPi, EPi, EPi,
        WPa, WPa, WPa, WPa, WPa,
        WKn, WBi, WQu, WKi, WTo,};

    // Positions Names
    public static final String[] POSITIONS = {
        "A8", "B8", "C8", "D8", "E8",
        "A7", "B7", "C7", "D7", "E7",
        "A6", "B6", "C6", "D6", "E6",
        "A5", "B5", "C5", "D5", "E5",
        "A4", "B4", "C4", "D4", "E4",
        "A3", "B3", "C3", "D3", "E3",
        "A2", "B2", "C2", "D2", "E2",
        "A1", "B1", "C1", "D1", "E1",};

    private int xAdjustment;
    private int yAdjustment;
    private String position;
    private String type;
    private ImageIcon imgIcon;

    public Piece(String type, String position) {
        this.type = type;
        this.position = position;

        if (!this.type.equals(EPi)) {
            URL url = MainWindow.class.getResource("/tn/rnu/isi/glsi/minichess/imgs/" + this.type + ".png");
            imgIcon = url != null ? new ImageIcon((URL) url) : new ImageIcon("null");
        } else {
            imgIcon = null;
        }
    }

    public int getxAdjustment() {
        return xAdjustment;
    }

    public void setxAdjustment(int xAdjustment) {
        this.xAdjustment = xAdjustment;
    }

    public int getyAdjustment() {
        return yAdjustment;
    }

    public void setyAdjustment(int yAdjustment) {
        this.yAdjustment = yAdjustment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ImageIcon getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(ImageIcon imgIcon) {
        this.imgIcon = imgIcon;
    }
}
