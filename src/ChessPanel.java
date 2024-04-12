import ChessPieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class ChessPanel extends JPanel implements MouseListener, ActionListener {

    Timer timer;
    boolean running = false;

    Cell[][] cells = new Cell[8][8];

    Pawn[] whitePawns = new Pawn[8];
    Pawn[] blackPawns = new Pawn[8];
    King whiteKing;
    King blackKing;
    Queen whiteQueen;
    Queen blackQueen;
    Rook[] whiteRooks = new Rook[2];
    Rook[] blackRooks = new Rook[2];
    Bishop[] whiteBishops = new Bishop[2];
    Bishop[] blackBishops = new Bishop[2];
    Knight[] whiteKnights = new Knight[2];
    Knight[] blackKnights = new Knight[2];

    ChessPiece selectedPiece;



    ChessPanel() {
        createGamePieces();
        addMouseListener(this);
        startGame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //background
        g.setColor(new Color(82, 60, 2));
        for (int x = 3; x < 720; x+=180) {
            for (int y = 3; y < 720; y+=180) {
                g.fillRect(x, y, 90, 90);
            }
        }
        for (int x = 93; x < 720; x+=180) {
            for (int y = 93; y < 720; y+=180) {
                g.fillRect(x, y, 90, 90);
            }
        }
        g.setColor(new Color(143, 119, 53));
        for (int x = 93; x < 720; x+=180) {
            for (int y = 3; y < 720; y+=180) {
                g.fillRect(x, y, 90, 90);
            }
        }
        for (int x = 3; x < 720; x+=180) {
            for (int y = 93; y < 720; y+=180) {
                g.fillRect(x, y, 90, 90);
            }
        }
        BufferedImage image;
        try {
            //white pieces
            //king
            image = ImageIO.read(new File("src/images/75px_white_king.png"));
            g.drawImage(image, whiteKing.x, whiteKing.y, this);
            //queen
            image = ImageIO.read(new File("src/images/75px_white_queen.png"));
            g.drawImage(image, whiteQueen.x, whiteQueen.y, this);
            //rooks
            image = ImageIO.read(new File("src/images/75px_white_rook.png"));
            for (Rook rook:whiteRooks) {
                g.drawImage(image, rook.x, rook.y, this);
            }
            //bishops
            image = ImageIO.read(new File("src/images/75px_white_bishop.png"));
            for (Bishop bishop:whiteBishops) {
                g.drawImage(image, bishop.x, bishop.y, this);
            }
            //knights
            image = ImageIO.read(new File("src/images/75px_white_knight.png"));
            for (Knight knight:whiteKnights) {
                g.drawImage(image, knight.x, knight.y, this);
            }
            //pawns
            image = ImageIO.read(new File("src/images/75px_white_pawn.png"));
            for (Pawn pawn:whitePawns) {
                g.drawImage(image, pawn.x, pawn.y, this);
            }

            //black pieces
            //king
            image = ImageIO.read(new File("src/images/75px_black_king.png"));
            g.drawImage(image, blackKing.x, blackKing.y, this);
            //queen
            image = ImageIO.read(new File("src/images/75px_black_queen.png"));
            g.drawImage(image, blackQueen.x, blackQueen.y, this);
            //rooks
            image = ImageIO.read(new File("src/images/75px_black_rook.png"));
            for (Rook rook:blackRooks) {
                g.drawImage(image, rook.x, rook.y, this);
            }
            //bishops
            image = ImageIO.read(new File("src/images/75px_black_bishop.png"));
            for (Bishop bishop:blackBishops) {
                g.drawImage(image, bishop.x, bishop.y, this);
            }
            //knights
            image = ImageIO.read(new File("src/images/75px_black_knight.png"));
            for (Knight knight:blackKnights) {
                g.drawImage(image, knight.x, knight.y, this);
            }
            //pawns
            image = ImageIO.read(new File("src/images/75px_black_pawn.png"));
            for (Pawn pawn:blackPawns) {
                g.drawImage(image, pawn.x, pawn.y, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void createGamePieces() {
        //cells
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell();
            }
        }

        //white pieces
        whiteKing = new King(4*90 + 10, 10, true);
        cells[4][0].pieceOnCell = 1;
        cells[4][0].side = true;

        whiteQueen = new Queen(3*90 + 10, 10, true);
        cells[3][0].pieceOnCell = 2;
        cells[3][0].side = true;

        for (int i = 0; i < 2; i++) {
            whiteRooks[i] = new Rook(i*90*7 + 10, 10, true);
            cells[i*7][0].side = true;
            cells[i*7][0].pieceOnCell = i+3;
        }

        for (int i = 1, j = 0; i < 7; i+=5, j++) {
            whiteBishops[j] = new Bishop(i*90 + 10, 10, true);
            cells[i][0].side = true;
            cells[i][0].pieceOnCell = j+5;
        }

        for (int i = 2, j = 0; i < 6; i+=3, j++) {
            whiteKnights[j] = new Knight(i*90 + 10, 10, true);
            cells[i][0].side = true;
            cells[i][0].pieceOnCell = j+7;
        }

        for (int i = 0; i < 8; i++) {
            whitePawns[i] = new Pawn(90*i + 10, 100, true);
            cells[i][1].side = true;
            cells[i][1].pieceOnCell = i+9;
        }

        //black pieces
        blackKing = new King(4*90 + 10, 7*90 + 10, true);
        cells[4][7].pieceOnCell = 1;
        cells[4][7].side = false;

        blackQueen = new Queen(3*90 + 10, 7*90 + 10, true);
        cells[3][7].pieceOnCell = 2;
        cells[3][7].side = false;

        for (int i = 0; i < 2; i++) {
            blackRooks[i] = new Rook(i*90*7 + 10, 7*90 + 10, true);
            cells[i*7][7].side = false;
            cells[i*7][7].pieceOnCell = i+3;
        }

        for (int i = 1, j = 0; i < 7; i+=5, j++) {
            blackBishops[j] = new Bishop(i*90 + 10, 7*90 + 10, true);
            cells[i][7].side = false;
            cells[i][7].pieceOnCell = j+5;
        }

        for (int i = 2, j = 0; i < 6; i+=3, j++) {
            blackKnights[j] = new Knight(i*90 + 10, 7*90 + 10, true);
            cells[i][7].side = false;
            cells[i][7].pieceOnCell = j+7;
        }

        for (int i = 0; i < 8; i++) {
            blackPawns[i] = new Pawn(90*i + 10, 6*90 + 10, true);
            cells[i][6].side = false;
            cells[i][6].pieceOnCell = i+9;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = (e.getX()/90);
        int y = (e.getY()/90);
        if (cells[x][y].side) {
            switch (cells[x][y].pieceOnCell) {
                case 0 :
                    break;
                case 1 :
                    selectedPiece = whiteKing;
                    break;
                case 2 :
                    selectedPiece = whiteQueen;
                    break;
                case 3 :
                    selectedPiece = whiteRooks[0];
                    break;
                case 4 :
                    selectedPiece = whiteRooks[1];
                    break;
                case 5 :
                    selectedPiece = whiteBishops[0];
                    break;
                case 6 :
                    selectedPiece =  whiteBishops[1];
                    break;
                case 7 :
                    selectedPiece = whiteKnights[0];
                    break;
                case 8 :
                    selectedPiece = whiteKnights[1];
                    break;
                case 9 :
                    selectedPiece = whitePawns[0];
                    break;
                case 10 :
                    selectedPiece = whitePawns[1];
                    break;
                case 11 :
                    selectedPiece = whitePawns[2];
                    break;
                case 12 :
                    selectedPiece = whitePawns[3];
                    break;
                case 13 :
                    selectedPiece = whitePawns[4];
                    break;
                case 14 :
                    selectedPiece = whitePawns[5];
                    break;
                case 15 :
                    selectedPiece = whitePawns[6];
                    break;
                case 16 :
                    selectedPiece = whitePawns[7];
                    break;

            }
        } else {
            switch (cells[x][y].pieceOnCell) {
                case 0 :
                    break;
                case 1 :
                    selectedPiece = blackKing;
                    break;
                case 2 :
                    selectedPiece = blackQueen;
                    break;
                case 3 :
                    selectedPiece = blackRooks[0];
                    break;
                case 4 :
                    selectedPiece = blackRooks[1];
                    break;
                case 5 :
                    selectedPiece = blackBishops[0];
                    break;
                case 6 :
                    selectedPiece =  blackBishops[1];
                    break;
                case 7 :
                    selectedPiece = blackKnights[0];
                    break;
                case 8 :
                    selectedPiece = blackKnights[1];
                    break;
                case 9 :
                    selectedPiece = blackPawns[0];
                    break;
                case 10 :
                    selectedPiece = blackPawns[1];
                    break;
                case 11 :
                    selectedPiece = blackPawns[2];
                    break;
                case 12 :
                    selectedPiece = blackPawns[3];
                    break;
                case 13 :
                    selectedPiece = blackPawns[4];
                    break;
                case 14 :
                    selectedPiece = blackPawns[5];
                    break;
                case 15 :
                    selectedPiece = blackPawns[6];
                    break;
                case 16 :
                    selectedPiece = blackPawns[7];
                    break;

            }
        }
        System.out.println("figure on the cell: " + cells[x][y].pieceOnCell + cells[x][y].side);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = (e.getX()/90);
        int y = (e.getY()/90);
        int oldX = (selectedPiece.x-10)/90;
        int oldY = (selectedPiece.y-10)/90;
        if (x != oldX || y != oldY) { //without this part of code if you click in one cell with game piece it will be just deleted
            if (selectedPiece.isLegalMove(oldX, oldY, x, y, selectedPiece.side)) {
                cells[x][y].pieceOnCell = cells[oldX][oldY].pieceOnCell;
                cells[x][y].side = cells[oldX][oldY].side;
                cells[oldX][oldY].pieceOnCell = 0;
                cells[oldX][oldY].side = false;
                selectedPiece.x = (x*90)+10;
                selectedPiece.y = (y*90)+10;
                selectedPiece = null;
            }
        }
        System.out.println("figure on the cell: " + cells[x][y].pieceOnCell + cells[x][y].side);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited");
    }

    public void startGame() {
        System.out.println("Game started");
        running = true;
        timer = new Timer(50, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {

        } repaint();
    }
}
