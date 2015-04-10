/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.rnu.isi.glsi.minichess.ui;

import tn.rnu.isi.glsi.minichess.core.Piece;
import tn.rnu.isi.glsi.minichess.core.Square;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Med
 */
public class Board extends JFrame implements MouseListener, MouseMotionListener {

    //intialize constants
    private final String TITLE = "MiniChess GLSI 2015";

    //intialize variables
    private Image boardImage1;
    private Image boardImage2;

    //intialize components
    private JPanel chessBoard = new JPanel();

    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();

    JLayeredPane layeredPane;
    Piece piece;
    int xAdjustment;
    int yAdjustment;

    //initialze arrays to hold squares and images of the board
    private Piece[] pieces = new Piece[40];
    private Square[] squares = new Square[40];

    public Board(Image boardImage1, Image boardImage2) {
        this.boardImage1 = boardImage1;
        this.boardImage2 = boardImage2;
        createUI();
    }

    private void createUI() {
        Dimension boardSize = new Dimension(354, 575);

        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        addComponentsToPane(getContentPane());

        // Configure Board Window
        setTitle(TITLE);
        setSize(boardSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds all the necessary components to the content pane of the JFrame, and
     * adds appropriate listeners to components.
     */
    private void addComponentsToPane(Container contentPane) {
        Dimension chessBoardSize = new Dimension(324, 519);

        layeredPane.add(chessBoard, BorderLayout.CENTER);
        chessBoard.setLayout(new GridLayout(8, 5));
        chessBoard.setPreferredSize(chessBoardSize);
        chessBoard.setBounds(-1, -2, chessBoardSize.width, chessBoardSize.height);

        // Adding labels
        addLabelsToHeadAndFooterPanels(northPanel);
        addLabelsToHeadAndFooterPanels(southPanel);
        addLabelsToSidePanels(westPanel);
        addLabelsToSidePanels(eastPanel);

        northPanel.setBackground(Color.white);
        southPanel.setBackground(Color.white);
        westPanel.setBackground(Color.white);
        eastPanel.setBackground(Color.white);

        //add all squares to frame
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(westPanel, BorderLayout.WEST);
        contentPane.add(eastPanel, BorderLayout.EAST);

        //call method to add squares and pieces to the center panel which holds the board
        addPanelsAndLabels();
    }

    private void addLabelsToHeadAndFooterPanels(JPanel panel) {
        panel.setLayout(new GridLayout(0, 5));
        JLabel[] lbls = new JLabel[5];
        String[] label = {"A", "B", "C", "D", "E"};

        for (int i = 0; i < 5; i++) {
            lbls[i] = new JLabel(label[i], SwingConstants.CENTER);
            lbls[i].setSize(20, 4);

            panel.add(lbls[i]);
        }
    }

    private void addLabelsToSidePanels(JPanel panel) {
        panel.setLayout(new GridLayout(8, 0));
        JLabel[] lbls = new JLabel[8];
        int[] num = {8, 7, 6, 5, 4, 3, 2, 1};
        for (int i = 0; i < 8; i++) {
            lbls[i] = new JLabel(" " + num[i] + " ");
            panel.add(lbls[i]);
        }
    }

    private void addPanelsAndLabels() {
        //call methd to create squares with backgound images and appropriate names
        addPanelsAndImages();

        for (int i = 0; i < squares.length; i++) {
            pieces[i] = new Piece(Piece.INITIALPOSITIONS[i], Piece.POSITIONS[i]);

            //used to know the postion of the label on the board
            pieces[i].setName(squares[i].getName());

            squares[i].add(pieces[i]);

            //adds squares created in addPanelsAndImages()
            chessBoard.add(squares[i]);
        }

        for (int i = 0; i < squares.length; i++) {
            addPiece(pieces[i]);
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
                //squares[count].setLayout(new BorderLayout());
                chessBoard.add(squares[count]);
                count++;
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        piece = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel) {
            return;
        }

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        piece = (Piece) c;
        piece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        piece.setSize(piece.getWidth(), piece.getHeight());
        layeredPane.add(piece, JLayeredPane.DRAG_LAYER);
    }

    //Move the chess piece around
    public void mouseDragged(MouseEvent me) {
        if (piece == null) {
            return;
        }
        piece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    //Drop the chess piece back onto the chess board
    public void mouseReleased(MouseEvent e) {
        if (piece == null) {
            return;
        }

        piece.setVisible(false);
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JLabel) {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add(piece);
        } else {
            Container parent = (Container) c;
            parent.add(piece);
        }

        piece.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    //method sets image of a label at a certain position in the board according to the block name i.e D4
    public void addPiece(Piece piece) {
        for (Piece p : pieces) {
            if (p.getName().equalsIgnoreCase(piece.getPosition())) {
                p.setIcon(piece.getImgIcon());
            }
        }
    }
}
