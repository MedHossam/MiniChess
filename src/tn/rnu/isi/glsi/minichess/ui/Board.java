/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.rnu.isi.glsi.minichess.ui;

import tn.rnu.isi.glsi.minichess.core.Piece;
import tn.rnu.isi.glsi.minichess.core.Square;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Med
 */
public class Board extends JFrame {

    //intialize constants

    private final String TITLE = "MiniChess GLSI 2015";

    //intialize variables
    private Image boardImage1;
    private Image boardImage2;
    //intialize components
    private JPanel centerPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    //initialze arrays to hold squares and images of the board
    private Piece[] pieces = new Piece[40];
    private Square[] squares = new Square[40];

    public Board(Image boardImage1, Image boardImage2) {
        this.boardImage1 = boardImage1;
        this.boardImage2 = boardImage2;
        createAndShowGUI();//call method to create gui
    }

    private void createAndShowGUI() {
        setTitle(TITLE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToPane(getContentPane());

        setSize(340, 570);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds all the necessary components to the content pane of the JFrame, and
     * adds appropriate listeners to components.
     */
    private void addComponentsToPane(Container contentPane) {

        GridLayout gridLayout = new GridLayout(8, 5);
        centerPanel.setLayout(gridLayout);

        //call mehod to add pieces to south panel
        addLabelsToSouthPanel();
        //call method to add oanels to west panel
        addLabelsToWestPanel();
        //call method to add squares and pieces to the center panel which holds the board
        addPanelsAndLabels();
        //add all squares to frame
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(westPanel, BorderLayout.WEST);
    }

    private void addLabelsToSouthPanel() {
        GridLayout gridLayout = new GridLayout(0, 5);

        southPanel.setLayout(gridLayout);
        JLabel[] lbls = new JLabel[5];
        String[] label = {"A", "B", "C", "D", "E"};

        for (int i = 0; i < 5; i++) {
            lbls[i] = new JLabel(label[i], SwingConstants.CENTER);
            lbls[i].setSize(20, 4);
            southPanel.add(lbls[i]);
        }
    }

    private void addLabelsToWestPanel() {
        GridLayout gridLayout = new GridLayout(8, 0);

        westPanel.setLayout(gridLayout);
        JLabel[] lbls = new JLabel[8];
        int[] num = {8, 7, 6, 5, 4, 3, 2, 1};
        for (int i = 0; i < 8; i++) {
            lbls[i] = new JLabel(num[i] + "");
            westPanel.add(lbls[i]);
        }
    }

    private void addPanelsAndLabels() {

        //call methd to create squares with backgound images and appropriate names
        addPanelsAndImages();

        for (int i = 0; i < squares.length; i++) {
            pieces[i] = new Piece();

            //used to know the postion of the label on the board
            pieces[i].setName(squares[i].getName());

            squares[i].add(pieces[i]);

            //adds squares created in addPanelsAndImages()
            centerPanel.add(squares[i]);
        }
    }

    //this method will create squares with backround images of chess board and set its name according to 1-8 for rows and A-H for coloumns
    private void addPanelsAndImages() {
        int count = 0;
        String[] label = {"A", "B", "C", "D", "E"};
        int[] num = {8, 7, 6, 5, 4, 3, 2, 1};

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                if ((col + row) % 2 == 0) {//even numbers get white pieces
                    squares[count] = new Square(boardImage1);
                } else {//odd numbers get black pieces
                    squares[count] = new Square(boardImage2);
                }

                squares[count].setName(label[col] + num[row]);
                count++;
            }
        }
    }

    //method sets image of a label at a certain position in the board according to the block name i.e D4
    public void addPiece(ImageIcon img, String block) {
        for (int s = 0; s < pieces.length; s++) {
            if (pieces[s].getName().equalsIgnoreCase(block)) {
                pieces[s].setIcon(img);
            }
        }
    }
}
