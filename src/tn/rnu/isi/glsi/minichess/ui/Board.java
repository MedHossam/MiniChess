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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
    Piece pieceSelected;

    //initialze arrays to hold squares and images of the board
    private Piece[][] pieces = new Piece[8][5];
    private Square[][] squares = new Square[8][5];

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
        int i = 0;
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                pieces[row][col] = new Piece(Piece.INITIALPOSITIONS[i], Piece.POSITIONS[i]);

                //used to know the postion of the label on the board
                pieces[row][col].setName(squares[row][col].getName());

                squares[row][col].add(pieces[row][col]);

                //adds squares created in addPanelsAndImages()
                chessBoard.add(squares[row][col]);
                i++;
            }
        }

        // Add pieces to initial positions
        for (Piece[] piecesCols : pieces) {
            for (Piece p : piecesCols) {
                addPiece(p);
            }
        }
    }

    //method sets image of a label at a certain position in the board according to the block name i.e D4
    public void addPiece(Piece piece) {
        for (Piece[] piecesCols : pieces) {
            for (Piece p : piecesCols) {
                if (p.getName().equalsIgnoreCase(piece.getPosition())) {
                    p.setIcon(piece.getImgIcon());
                }
            }
        }
    }

    //this method will create squares with backround images of chess board and set its name according to 1-8 for rows and A-H for coloumns
    private void addPanelsAndImages() {
        //int count = 0;
        String[] label = {"A", "B", "C", "D", "E"};
        int[] num = {8, 7, 6, 5, 4, 3, 2, 1};

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                if ((col + row) % 2 == 0) {//even numbers get white pieces
                    squares[row][col] = new Square(boardImage1);
                } else {//odd numbers get black pieces
                    squares[row][col] = new Square(boardImage2);
                }
                squares[row][col].setName(label[col] + num[row]);
                chessBoard.add(squares[row][col]);
                //count++;
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        pieceSelected = null;
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel) {
            return;
        }

        Point parentLocation = c.getParent().getLocation();
        pieceSelected = (Piece) c;

        pieceSelected.setxAdjustment(parentLocation.x - e.getX() - 1);
        pieceSelected.setyAdjustment(parentLocation.y - e.getY() + 3);

        showPossibleDestinations(pieceSelected);

        pieceSelected.setLocation(e.getX() + pieceSelected.getxAdjustment(), e.getY() + pieceSelected.getyAdjustment());
        pieceSelected.setSize(pieceSelected.getWidth(), pieceSelected.getHeight());

        layeredPane.add(pieceSelected, JLayeredPane.DRAG_LAYER);
    }

    // Show possible destinations on the chess board
    public void showPossibleDestinations(Piece p) {
        // TODO Show possible positions : this method will be in class blow
        String rowIndexes = "ABCDE";
        String colIndexes = "12345678";
        int currentCol = rowIndexes.indexOf(p.getName().charAt(0));
        int currentRow = colIndexes.indexOf(p.getName().charAt(1));
        int newCol, newRow;

        switch (p.getType()) {
            // White pieces
            case Piece.WPa:
                newCol = currentCol;
                newRow = 6 - currentRow;
                if ((newCol + newRow) % 2 == 0) {//even numbers get white pieces
                    squares[newRow][newCol].setImage(boardImage2);
                } else {//odd numbers get black pieces
                    squares[newRow][newCol].setImage(boardImage1);
                }
                break;

            case Piece.WBi:
                break;

            case Piece.WKi:
                break;

            case Piece.WKn:
                break;

            case Piece.WQu:
                break;

            case Piece.WTo:
                break;

            // Black Pieces
            case Piece.BPa:
                break;

            case Piece.BBi:
                break;

            case Piece.BKi:
                break;

            case Piece.BKn:
                break;

            case Piece.BQu:
                break;

            case Piece.BTo:
                break;
            default:
                return;
        }
    }

    //Move the chess pieceSelected around
    public void mouseDragged(MouseEvent me) {
        if (pieceSelected == null) {
            return;
        }
        pieceSelected.setLocation(me.getX() + pieceSelected.getxAdjustment(), me.getY() + pieceSelected.getyAdjustment());
    }

    //Drop the chess pieceSelected back onto the chess board
    public void mouseReleased(MouseEvent e) {
        rebuildSquaresBackground();

        if (pieceSelected == null) {
            return;
        }

        pieceSelected.setVisible(false);
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        Container parentContainer;

        if (c instanceof JLabel) {
            parentContainer = c.getParent();
            parentContainer.remove(0);
        } else {
            parentContainer = (Container) c;
        }

        pieceSelected.setName(c.getName());

        System.out.println("========== Piece Selected ==========");
        System.out.println("Piece Type : " + pieceSelected.getType() + "   |   Position : " + pieceSelected.getName());

        parentContainer.add(pieceSelected);
        pieceSelected.setVisible(true);
    }

    // Rebuild UI (Squares Background)
    private void rebuildSquaresBackground() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                if ((col + row) % 2 == 0) {//even numbers get white pieces
                    squares[row][col].setImage(boardImage1);
                } else {//odd numbers get black pieces
                    squares[row][col].setImage(boardImage2);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());
        if (c instanceof Piece) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
