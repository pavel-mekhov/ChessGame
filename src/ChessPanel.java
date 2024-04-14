import ChessPieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
    ChessPiece aimedPiece;
    Cell aimedCell;
    boolean activeSide;
    String winnerSide;



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
        g.setColor(new Color(199, 180, 129));
        g.fillRect(720, 0, 400, 900);

        //sign about active side
        g.setColor(Color.BLACK);
        g.setFont(new Font("Lora", Font.ITALIC, 40));
        String activeSideStr;
        if (activeSide) {
            activeSideStr = "White";
        } else
            activeSideStr = "Black";
        g.drawString("Active side: " + activeSideStr, 730, 60);


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

            //win side message
            if (winnerSide != null) {
                g.setColor(new Color(143, 119, 53));
                g.fillRect(0,0,1100, 800);
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Lora", Font.ITALIC, 40));
                g.drawString(winnerSide + " is winner!", 360, 380);
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
        cells[4][0].side = "white";

        whiteQueen = new Queen(3*90 + 10, 10, true);
        cells[3][0].pieceOnCell = 2;
        cells[3][0].side = "white";

        for (int i = 0, j = 0; i < 2; i++, j+=7) {
            whiteRooks[i] = new Rook(j*90 + 10, 10, true);
            cells[j][0].side = "white";
            cells[j][0].pieceOnCell = i+3;
        }

        for (int i = 1, j = 0; i < 7; i+=5, j++) {
            whiteBishops[j] = new Bishop(i*90 + 10, 10, true);
            cells[i][0].side = "white";
            cells[i][0].pieceOnCell = j+5;
        }

        for (int i = 2, j = 0; i < 6; i+=3, j++) {
            whiteKnights[j] = new Knight(i*90 + 10, 10, true);
            cells[i][0].side = "white";
            cells[i][0].pieceOnCell = j+7;
        }

        for (int i = 0; i < 8; i++) {
            whitePawns[i] = new Pawn(90*i + 10, 100, true);
            cells[i][1].side = "white";
            cells[i][1].pieceOnCell = i+9;
        }

        //black pieces
        blackKing = new King(4*90 + 10, 7*90 + 10, false);
        cells[4][7].pieceOnCell = 1;
        cells[4][7].side = "black";

        blackQueen = new Queen(3*90 + 10, 7*90 + 10, false);
        cells[3][7].pieceOnCell = 2;
        cells[3][7].side = "black";

        for (int i = 0, j = 0; i < 2; i++, j+=7) {
            blackRooks[i] = new Rook(j*90 + 10, 7*90 + 10, false);
            cells[j][7].side = "black";
            cells[j][7].pieceOnCell = i+3;
        }

        for (int i = 1, j = 0; i < 7; i+=5, j++) {
            blackBishops[j] = new Bishop(i*90 + 10, 7*90 + 10, false);
            cells[i][7].side = "black";
            cells[i][7].pieceOnCell = j+5;
        }

        for (int i = 2, j = 0; i < 6; i+=3, j++) {
            blackKnights[j] = new Knight(i*90 + 10, 7*90 + 10, false);
            cells[i][7].side = "black";
            cells[i][7].pieceOnCell = j+7;
        }

        for (int i = 0; i < 8; i++) {
            blackPawns[i] = new Pawn(90*i + 10, 6*90 + 10, false);
            cells[i][6].side = "black";
            cells[i][6].pieceOnCell = i+9;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //refactoring coordinates to format of cells' array [0-7][0-7]
        int x = (e.getX()/90);
        int y = (e.getY()/90);
        selectedPiece = selectPiece(x, y);
        System.out.println("figure on the cell: " + cells[x][y].pieceOnCell + cells[x][y].side);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = (e.getX()/90);
        int y = (e.getY()/90);
        int oldX = (selectedPiece.x-10)/90;
        int oldY = (selectedPiece.y-10)/90;
        aimedCell = cells[x][y];

        if (x != oldX || y != oldY) { //checks if piece was moved
            if (selectedPiece.side == activeSide) { //checks if moved piece is from active side
                if (selectedPiece.isLegalMove(oldX, oldY, x, y, selectedPiece.side, aimedCell.side)) { //checks if it's ok to move piece the way user tries
                    //"eating" game pieces
                    if (cells[x][y].pieceOnCell >0) { //checks if cell empty
                        if (!cells[x][y].side.equals(cells[oldX][oldY].side)) { //checks if piece on cell is from different side
                            aimedPiece = selectPiece(x, y); //sets aimed piece
                            if (selectedPiece.side) { //sorts black and white pieces to different "storage"
                                aimedPiece.y = 381;
                            } else {
                                aimedPiece.y = 150;
                            }
                            aimedPiece.x = 690 + cells[x][y].pieceOnCell*22;
                        }
                    }
                    if (!cells[oldX][oldY].side.equals(cells[x][y].side)) { //checks if cell is already taken
                        cells[x][y].pieceOnCell = cells[oldX][oldY].pieceOnCell;
                        cells[x][y].side = cells[oldX][oldY].side;
                        cells[oldX][oldY].pieceOnCell = 0;
                        cells[oldX][oldY].side = "empty";
                        selectedPiece.x = (x*90)+10;
                        selectedPiece.y = (y*90)+10;
                        selectedPiece = null;

                        //switching active side
                        isKingAlive();
                        activeSide = !activeSide;
                    }
                }
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
        activeSide = true;
    }

    public void isKingAlive() {
        if (whiteKing.x > 700) {
            winnerSide = "Black side";
        } else if (blackKing.x > 700) {
            winnerSide = "White side";
        }
        running = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            isKingAlive();
        } repaint();
    }

    public ChessPiece selectPiece(int x, int y) { //method to return link to game piece by its coordinates
        if (cells[x][y].side.equals("white")) { //white side pieces
            switch (cells[x][y].pieceOnCell) {
                case 1 -> {
                    return whiteKing;
                }
                case 2 -> {
                    return whiteQueen;
                }
                case 3 -> {
                    return whiteRooks[0];
                }
                case 4 -> {
                    return whiteRooks[1];
                }
                case 5 -> {
                    return whiteBishops[0];
                }
                case 6 -> {
                    return whiteBishops[1];
                }
                case 7 -> {
                    return whiteKnights[0];
                }
                case 8 -> {
                    return whiteKnights[1];
                }
                case 9 -> {
                    return whitePawns[0];
                }
                case 10 -> {
                    return whitePawns[1];
                }
                case 11 -> {
                    return whitePawns[2];
                }
                case 12 -> {
                    return whitePawns[3];
                }
                case 13 -> {
                    return whitePawns[4];
                }
                case 14 -> {
                    return whitePawns[5];
                }
                case 15 -> {
                    return whitePawns[6];
                }
                case 16 -> {
                    return whitePawns[7];
                }
            }
        } else { //black side pieces
            switch (cells[x][y].pieceOnCell) {
                case 1 -> {
                    return blackKing;
                }
                case 2 -> {
                    return blackQueen;
                }
                case 3 -> {
                    return blackRooks[0];
                }
                case 4 -> {
                    return blackRooks[1];
                }
                case 5 -> {
                    return blackBishops[0];
                }
                case 6 -> {
                    return blackBishops[1];
                }
                case 7 -> {
                    return blackKnights[0];
                }
                case 8 -> {
                    return blackKnights[1];
                }
                case 9 -> {
                    return blackPawns[0];
                }
                case 10 -> {
                    return blackPawns[1];
                }
                case 11 -> {
                    return blackPawns[2];
                }
                case 12 -> {
                    return blackPawns[3];
                }
                case 13 -> {
                    return blackPawns[4];
                }
                case 14 -> {
                    return blackPawns[5];
                }
                case 15 -> {
                    return blackPawns[6];
                }
                case 16 -> {
                    return blackPawns[7];
                }
            }
        }
        return null;
    }
}
