/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.rnu.isi.glsi.minichess.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import tn.rnu.isi.glsi.minichess.core.Piece;

/**
 *
 * @author Med
 */
public class MainWindow {

    public MainWindow() {
        initBoard();
    }

    private static void initBoard() {
        System.out.println("======== initBoard() ======= ");
        Image blackBlock;
        Image whiteBlock;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        URL urlN = MainWindow.class.getResource("/tn/rnu/isi/glsi/minichess/imgs/blackBlock.jpg");
        URL urlB = MainWindow.class.getResource("/tn/rnu/isi/glsi/minichess/imgs/whiteBlock.jpg");

        ImageIcon iconN = urlN != null ? new ImageIcon((URL) urlN) : new ImageIcon("null");
        ImageIcon iconB = urlB != null ? new ImageIcon((URL) urlB) : new ImageIcon("null");

        blackBlock = toolkit.getImage(urlN);
        whiteBlock = toolkit.getImage(urlB);

        Board board = new Board(whiteBlock, blackBlock);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new MainWindow();
    }
}
